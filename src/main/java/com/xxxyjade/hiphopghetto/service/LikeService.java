package com.xxxyjade.hiphopghetto.service;

public interface LikeService {

    /**
     * 是否点赞
     */
    Boolean select(Long resourceId);

    /**
     * 点赞
     */
    void like(Long resourceId);

    /**
     * 取消点赞
     */
    void cancel(Long resourceId);

}
