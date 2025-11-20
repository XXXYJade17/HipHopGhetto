package com.xxxyjade.hiphopghetto.server.collection.service.impl;

import com.xxxyjade.hiphopghetto.common.constant.MessageQueue;
import com.xxxyjade.hiphopghetto.common.constant.MessageType;
import com.xxxyjade.hiphopghetto.message.domain.Message;
import com.xxxyjade.hiphopghetto.model.entity.Collection;
import com.xxxyjade.hiphopghetto.server.collection.service.CollectionMessageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class CollectionMessageServiceImpl implements CollectionMessageService {

    private final RabbitTemplate rabbitTemplate;

    /**
     * 发送收藏数递增消息
     */
    public void sendCollectionCountIncreaseMessage(Collection collection) {
        Message<Collection> message = Message.<Collection>builder()
                .messageType(MessageType.COLLECTION_COUNT_INCREASE)
                .messageBody(collection)
                .build();
        log.info("发送收藏数递增消息: {}", message);
        rabbitTemplate.convertAndSend(
                MessageQueue.COLLECTION_QUEUE,
                message
        );
    }

    /**
     * 发送收藏数递减消息
     */
    public void sendCollectionCountDecreaseMessage(Collection collection) {
        Message<Collection> message = Message.<Collection>builder()
                .messageType(MessageType.COLLECTION_COUNT_DECREASE)
                .messageBody(collection)
                .build();
        log.info("发送收藏数递减消息: {}", message);
        rabbitTemplate.convertAndSend(
                MessageQueue.COLLECTION_QUEUE,
                message
        );
    }

}
