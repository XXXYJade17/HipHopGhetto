package com.xxxyjade.hiphopghetto.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xxxyjade.hiphopghetto.common.pojo.dto.ScoreCountDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.ScoreDTO;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Score;
import com.xxxyjade.hiphopghetto.mapper.ScoreMapper;
import com.xxxyjade.hiphopghetto.service.ScoreService;
import com.xxxyjade.hiphopghetto.util.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ScoreServiceImpl implements ScoreService {

    @Autowired
    private ScoreMapper scoreMapper;

    /**
     * 查询评分
     */
    public Integer select(Long resourceId) {
        return scoreMapper.selectOne(
                new QueryWrapper<Score>()
                        .eq("user_id", ThreadUtil.getUserId())
                        .eq("resource_id", resourceId)
        ).getScore();
    }

    /**
     * 评分
     */
    public void score(ScoreDTO scoreDTO) {
        scoreMapper.insertOrUpdateScore(
                Score.builder()
                        .userId(ThreadUtil.getUserId())
                        .resourceId(scoreDTO.getResourceId())
                        .score(scoreDTO.getScore())
                        .build()
        );
    }

    /**
     * 查询分数计数
     */
    public List<ScoreCountDTO> selectScoreCount(List<Integer> ids) {
        return scoreMapper.selectScoreCountByIds(ids);
    }

}
