package com.xxxyjade.hiphopghetto.server.like.service;

import com.xxxyjade.hiphopghetto.model.entity.Like;

public interface LikeMessageService {

    /**
     * 发送收藏数递增消息
     */
    void sendLikeCountIncreaseMessage(Like like);

    /**
     * 发送收藏数递减消息
     */
    void sendLikeCountDecreaseMessage(Like like);

}
