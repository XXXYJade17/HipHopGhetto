package com.xxxyjade.hiphopghetto.server.like.service;

import com.xxxyjade.hiphopghetto.model.dto.LikeDTO;

import java.util.List;
import java.util.Map;

public interface LikeService {

    /**
     * 查询点赞状态
     */
    Boolean select(Long id);

    /**
     * 点赞
     */
    void like(LikeDTO likeDTO);

    /**
     * 取消点赞
     */
    void unlike(LikeDTO likeDTO);

    /**
     * 批量查询点赞状态
     */
    Map<Long, Boolean> selectBatch(Long userId, List<Long> ids);

}
