package com.xxxyjade.hiphopghetto.service;

import com.xxxyjade.hiphopghetto.common.pojo.dto.RatingCreateDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.RatingUpdateDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.ScoreCountDTO;

import java.util.List;

public interface RatingService {

    /**
     * 查询评分
     */
    Integer select(Long resourceId);

    /**
     * 创建评分
     */
    void create(RatingCreateDTO ratingCreateDTO);

    /**
     * 更新评分
     */
    void update(RatingUpdateDTO ratingUpdateDTO);

    /**
     * 查询分数计数
     */
    List<ScoreCountDTO> selectScoreCount(Integer resourceType);

}
