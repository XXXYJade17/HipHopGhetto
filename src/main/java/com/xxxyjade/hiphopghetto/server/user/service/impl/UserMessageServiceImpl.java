package com.xxxyjade.hiphopghetto.server.user.service.impl;

import com.xxxyjade.hiphopghetto.common.constant.MessageQueue;
import com.xxxyjade.hiphopghetto.common.constant.MessageType;
import com.xxxyjade.hiphopghetto.message.domain.Message;
import com.xxxyjade.hiphopghetto.model.entity.User;
import com.xxxyjade.hiphopghetto.server.user.service.UserMessageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class UserMessageServiceImpl implements UserMessageService {

    private final RabbitTemplate rabbitTemplate;

    /**
     * 发送用户信息创建消息
     */
    public void sendUserInfoCreateMessage(User user) {
        Message<User> message = Message.<User>builder()
                .messageType(MessageType.USER_INFO_CREATE)
                .messageBody(user)
                .build();
        log.info("发送用户信息创建消息:{}", message);
        rabbitTemplate.convertAndSend(
                MessageQueue.USER_QUEUE,
                message
        );
    }

}
