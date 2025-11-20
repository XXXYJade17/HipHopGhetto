package com.xxxyjade.hiphopghetto.server.user.service;

import com.xxxyjade.hiphopghetto.model.entity.User;

public interface UserMessageService {

    /**
     * 发送用户信息创建消息
     */
    void sendUserInfoCreateMessage(User user);

}
