package com.xxxyjade.hiphopghetto.message.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Message<T> {

    /**
     * 消息类型标识
     */
    private String messageType;

    /**
     * 消息体
     */
    private T messageBody;

}
