package com.xxxyjade.hiphopghetto.service;

import com.xxxyjade.hiphopghetto.common.pojo.dto.ScoreCountDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.ScoreDTO;

import java.util.List;

public interface ScoreService {

    /**
     * 查询评分
     */
    Integer select(Long resourceId);

    /**
     * 评分
     */
    void score(ScoreDTO scoreDTO);

    /**
     * 查询分数计数
     */
    List<ScoreCountDTO> selectScoreCount(Integer resourceType);

}
