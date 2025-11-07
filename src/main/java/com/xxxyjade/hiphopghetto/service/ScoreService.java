package com.xxxyjade.hiphopghetto.service;

import com.xxxyjade.hiphopghetto.common.pojo.dto.ScoreDTO;

public interface ScoreService {

    /**
     * 查询评分
     */
    Integer select(Long resourceId);

    /**
     * 评分
     */
    void score(ScoreDTO scoreDTO);

}
