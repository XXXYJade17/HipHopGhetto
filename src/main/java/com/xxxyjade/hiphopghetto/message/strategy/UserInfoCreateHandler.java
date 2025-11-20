package com.xxxyjade.hiphopghetto.message.strategy;

import com.xxxyjade.hiphopghetto.common.constant.MessageType;
import com.xxxyjade.hiphopghetto.model.entity.User;
import com.xxxyjade.hiphopghetto.message.domain.Message;
import com.xxxyjade.hiphopghetto.server.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserInfoCreateHandler implements MessageStrategy<User> {

    @Autowired
    private UserService userService;

    /**
     * 处理消息
     * 创建用户信息
     */
    public void handle(Message<User> message) {
        User user = message.getMessageBody();
        userService.createInfo(
                User.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .build()
        );
    }

    /**
     * 消息类型标识
     */
    public String getMessageType() {
        return MessageType.USER_INFO_CREATE;
    }

}
