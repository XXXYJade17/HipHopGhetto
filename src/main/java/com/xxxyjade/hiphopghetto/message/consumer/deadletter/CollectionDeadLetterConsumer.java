package com.xxxyjade.hiphopghetto.message.consumer.deadletter;

import com.xxxyjade.hiphopghetto.common.constant.MessageQueue;
import com.xxxyjade.hiphopghetto.model.entity.Collection;
import com.xxxyjade.hiphopghetto.config.RabbitConfig;
import com.xxxyjade.hiphopghetto.message.domain.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
public class CollectionDeadLetterConsumer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    // 记录消息重试次数
    private final Map<String, AtomicInteger> retryCountMap = new ConcurrentHashMap<>();

    // 监听收藏死信队列
    @RabbitListener(queues = MessageQueue.COLLECTION_DEAD_QUEUE)
    public void handleDeadLetterMessage(Message<Collection> message) {
        String messageId = message.getMessageId();
        AtomicInteger retryCount = retryCountMap.computeIfAbsent(
                messageId,
                k -> new AtomicInteger(0)
        );
        
        log.info("接受到死信消息: {}, 当前尝试次数: {}", message, retryCount.get());
        
        // 如果重试次数小于3次，重新发送到原队列
        if (retryCount.incrementAndGet() <= 3) {
            log.info("当前尝试消息Id: {}", messageId);
            // 延迟3秒后重试
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            rabbitTemplate.convertAndSend(MessageQueue.COLLECTION_QUEUE, message);
        } else {
            log.error("超过重试次数，发送到永久失败队列: {}", message);
            rabbitTemplate.convertAndSend(MessageQueue.DEAD_LETTER_QUEUE, message);
            retryCountMap.remove(messageId);
        }
    }
}