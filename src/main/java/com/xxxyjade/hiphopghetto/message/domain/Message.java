package com.xxxyjade.hiphopghetto.message.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message<T> {

    /**
     * 消息唯一标识
     */
    @Builder.Default
    private String messageId = UUID.randomUUID().toString().replaceAll("-", "");

    /**
     * 消息类型标识
     */
    private String messageType;

    /**
     * 消息体
     */
    private T messageBody;

}
