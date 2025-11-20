package com.xxxyjade.hiphopghetto.server.rating.service;

import com.xxxyjade.hiphopghetto.model.dto.RatingDTO;

public interface RatingService {

    /**
     * 查询用户评分
     */
    Integer select(Long targetId);

    /**
     * 用户评分
     */
    void rate(RatingDTO ratingDTO);

}
