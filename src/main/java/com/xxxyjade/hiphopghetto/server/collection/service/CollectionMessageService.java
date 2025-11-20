package com.xxxyjade.hiphopghetto.server.collection.service;

import com.xxxyjade.hiphopghetto.model.entity.Collection;

public interface CollectionMessageService {

    /**
     * 发送收藏数递增消息
     */
    void sendCollectionCountIncreaseMessage(Collection collection);

    /**
     * 发送收藏数递减消息
     */
    void sendCollectionCountDecreaseMessage(Collection collection);

}
