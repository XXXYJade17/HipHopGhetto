package com.xxxyjade.hiphopghetto.service;

public interface CollectService {

    /**
     * 是否收藏
     */
    Boolean select(Long resourceId);

    /**
     * 收藏
     */
    void collect(Long resourceId);

    /**
     * 取消收藏
     */
    void cancel(Long resourceId);

}
