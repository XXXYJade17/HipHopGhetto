package com.xxxyjade.hiphopghetto.server.comment.service.impl;

import com.xxxyjade.hiphopghetto.common.constant.MessageQueue;
import com.xxxyjade.hiphopghetto.common.constant.MessageType;
import com.xxxyjade.hiphopghetto.message.domain.Message;
import com.xxxyjade.hiphopghetto.model.entity.Comment;
import com.xxxyjade.hiphopghetto.server.comment.service.CommentMessageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class CommentMessageServiceImpl implements CommentMessageService {

    private final RabbitTemplate rabbitTemplate;

    /**
     * 发送评论数递增消息
     */
    public void sentCommentCountIncreaseMessage(Comment comment) {
        Message<Comment> message = Message.<Comment>builder()
                .messageType(MessageType.COMMENT_COUNT_INCREASE)
                .messageBody(comment)
                .build();
        log.info("发送评论数递增消息:{}", message);
        rabbitTemplate.convertAndSend(
                MessageQueue.COMMENT_QUEUE,
                message
        );
    }

    /**
     * 发送评论数递减消息
     */
    public void sentCommentCountDecreaseMessage(Comment comment) {
        Message<Comment> message = Message.<Comment>builder()
                .messageType(MessageType.COMMENT_COUNT_DECREASE)
                .messageBody(comment)
                .build();
        log.info("发送评论数递减消息:{}", message);
        rabbitTemplate.convertAndSend(
                MessageQueue.COMMENT_QUEUE,
                message
        );
    }

}
