package com.xxxyjade.hiphopghetto.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxyjade.hiphopghetto.common.pojo.dto.ScoreCountDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.ScoreDTO;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Album;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Score;
import com.xxxyjade.hiphopghetto.common.pojo.entity.ScoreStats;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Song;
import com.xxxyjade.hiphopghetto.mapper.ScoreMapper;
import com.xxxyjade.hiphopghetto.service.AlbumService;
import com.xxxyjade.hiphopghetto.service.ScoreService;
import com.xxxyjade.hiphopghetto.service.SongService;
import com.xxxyjade.hiphopghetto.util.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ScoreServiceImpl implements ScoreService {

    @Autowired
    private AlbumService albumService;
    @Autowired
    private ScoreMapper scoreMapper;
    @Autowired
    private SongService songService;

    /**
     * 查询评分
     */
    public Integer select(Long resourceId) {
        Score score = scoreMapper.selectOne(
                new QueryWrapper<Score>()
                        .eq("user_id", ThreadUtil.getUserId())
                        .eq("resource_id", resourceId)
        );
        return score == null ? -1 : score.getScore();
    }

    /**
     * 评分
     */
    public void score(ScoreDTO scoreDTO) {
        scoreMapper.insertOrUpdateScore(
                Score.builder()
                        .userId(ThreadUtil.getUserId())
                        .resourceId(scoreDTO.getResourceId())
                        .resourceType(scoreDTO.getResourceType())
                        .score(scoreDTO.getScore())
                        .build()
        );
    }

    /**
     * 查询分数计数
     */
    public List<ScoreCountDTO> selectScoreCount(Integer resourceType) {
        return  scoreMapper.selectScoreCount(resourceType);
    }

}
