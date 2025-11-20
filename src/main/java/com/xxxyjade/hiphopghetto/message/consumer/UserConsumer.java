package com.xxxyjade.hiphopghetto.message.consumer;

import com.xxxyjade.hiphopghetto.model.entity.User;
import com.xxxyjade.hiphopghetto.config.RabbitConfig;
import com.xxxyjade.hiphopghetto.message.context.MessageStrategyDispatcher;
import com.xxxyjade.hiphopghetto.message.domain.Message;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserConsumer {

    @Autowired
    private MessageStrategyDispatcher messageStrategyDispatcher;

    @RabbitListener(queues = RabbitConfig.USER_QUEUE)
    @SneakyThrows
    public void handleUserInfoMessage(Message<User> message) {
        log.info("Received user info message: {}", message);
        messageStrategyDispatcher.dispatch(message);
    }

}
