package com.xxxyjade.hiphopghetto.server.like.service.impl;

import com.xxxyjade.hiphopghetto.common.constant.MessageQueue;
import com.xxxyjade.hiphopghetto.common.constant.MessageType;
import com.xxxyjade.hiphopghetto.message.domain.Message;
import com.xxxyjade.hiphopghetto.model.entity.Like;
import com.xxxyjade.hiphopghetto.server.like.service.LikeMessageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class LikeMessageServiceImpl implements LikeMessageService {

    private final RabbitTemplate rabbitTemplate;

    /**
     * 发送收藏数递增消息
     */
    public void sendLikeCountIncreaseMessage(Like like) {
        Message<Like> message = Message.<Like>builder()
                .messageType(MessageType.LIKE_COUNT_INCREASE)
                .messageBody(like)
                .build();
        log.info("发送收藏数递增消息: {}", message);
        rabbitTemplate.convertAndSend(
                MessageQueue.LIKE_QUEUE,
                message
        );
    }

    /**
     * 发送收藏数递减消息
     */
    public void sendLikeCountDecreaseMessage(Like like) {
        Message<Like> message = Message.<Like>builder()
                .messageType(MessageType.LIKE_COUNT_DECREASE)
                .messageBody(like)
                .build();
        log.info("发送收藏数递减消息: {}", message);
        rabbitTemplate.convertAndSend(
                MessageQueue.LIKE_QUEUE,
                message
        );
    }

}
