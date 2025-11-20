package com.xxxyjade.hiphopghetto.message.consumer;

import com.xxxyjade.hiphopghetto.common.constant.MessageQueue;
import com.xxxyjade.hiphopghetto.model.entity.Comment;
import com.xxxyjade.hiphopghetto.config.RabbitConfig;
import com.xxxyjade.hiphopghetto.message.context.MessageStrategyDispatcher;
import com.xxxyjade.hiphopghetto.message.domain.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CommentConsumer {

    @Autowired
    private MessageStrategyDispatcher messageStrategyDispatcher;

    // 监听收藏计数队列
    @RabbitListener(queues = MessageQueue.COMMENT_QUEUE)
    public void handleCollectCount(Message<Comment> message) {
        log.info("接收到消息: {}", message);
        messageStrategyDispatcher.dispatch(message);
    }

}
