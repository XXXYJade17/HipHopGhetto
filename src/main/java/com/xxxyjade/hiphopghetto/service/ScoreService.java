package com.xxxyjade.hiphopghetto.service;

import com.xxxyjade.hiphopghetto.common.pojo.dto.ScoreCreateDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.ScoreDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.PageQueryDTO;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Score;
import com.xxxyjade.hiphopghetto.common.pojo.vo.ScoreDetailVO;
import com.xxxyjade.hiphopghetto.common.pojo.vo.PageVO;

public interface ScoreService {

    /**
     * 创建评分
     * @param scoreCreateDTO 评分创建DTO
     */
    Void create(ScoreCreateDTO scoreCreateDTO);

    /**
     * 评分
     * @param scoreDTO 评分DTO
     */
    Void vote(ScoreDTO scoreDTO);

    /**
     * 分页查询
     * @param pageQueryDTO 分页查询DTO
     * @return 分页查询VO
     */
    PageVO<Score> page(PageQueryDTO pageQueryDTO);

    /**
     * 详情查询
     * @param scoreId 评分ID
     * @return 评分详情VO
     */
    ScoreDetailVO detail(Long scoreId);

}
