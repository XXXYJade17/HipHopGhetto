package com.xxxyjade.hiphopghetto.message.consumer;

import com.xxxyjade.hiphopghetto.common.constant.MessageQueue;
import com.xxxyjade.hiphopghetto.model.entity.Rating;
import com.xxxyjade.hiphopghetto.config.RabbitConfig;
import com.xxxyjade.hiphopghetto.message.context.MessageStrategyDispatcher;
import com.xxxyjade.hiphopghetto.message.domain.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RatingConsumer {

    @Autowired
    private MessageStrategyDispatcher messageStrategyDispatcher;

    @RabbitListener(queues = MessageQueue.RATING_QUEUE)
    public void handleUserInfoMessage(Message<Rating> message) {
        log.info("接收到消息: {}", message);
        messageStrategyDispatcher.dispatch(message);
    }

}
