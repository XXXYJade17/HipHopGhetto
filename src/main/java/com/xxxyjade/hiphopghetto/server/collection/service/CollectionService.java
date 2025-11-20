package com.xxxyjade.hiphopghetto.server.collection.service;

import com.xxxyjade.hiphopghetto.model.dto.CollectionDTO;

public interface CollectionService {

    /**
     * 查询收藏状态
     */
    Boolean select(Long targetId);

    /**
     * 创建收藏
     */
    void collect(CollectionDTO collectionDTO);

    /**
     * 取消收藏
     */
    void uncollect(CollectionDTO collectionDTO);

}
