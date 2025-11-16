package com.xxxyjade.hiphopghetto.message.strategy;

import com.xxxyjade.hiphopghetto.message.domain.Message;

public interface MessageStrategy<T> {

    // 消息处理方法
    void handle(Message<T> message);

    // 消息类型标识
    String getMessageType();

}