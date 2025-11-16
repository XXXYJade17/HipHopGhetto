package com.xxxyjade.hiphopghetto.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xxxyjade.hiphopghetto.common.constant.MessageTypeConstant;
import com.xxxyjade.hiphopghetto.common.pojo.dto.RatingCreateDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.RatingUpdateDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.ScoreCountDTO;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Rating;
import com.xxxyjade.hiphopghetto.config.RabbitConfig;
import com.xxxyjade.hiphopghetto.mapper.RatingMapper;
import com.xxxyjade.hiphopghetto.message.domain.Message;
import com.xxxyjade.hiphopghetto.service.RatingService;
import com.xxxyjade.hiphopghetto.util.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private RatingMapper ratingMapper;

    /**
     * 查询用户评分
     */
    public Integer select(Long targetId) {
        Long userId = ThreadUtil.getUserId();
        QueryWrapper<Rating> wrapper = new QueryWrapper<Rating>()
                .eq("user_id", userId)
                .eq("target_id", targetId);

        Rating rating = ratingMapper.selectOne(wrapper);
        return rating == null ? -1 : rating.getScore();
    }

    /**
     * 创建评分
     */
    public void create(RatingCreateDTO ratingCreateDTO) {
        Long userId = ThreadUtil.getUserId();
        Long targetId = ratingCreateDTO.getTargetId();
        Integer targetType = ratingCreateDTO.getTargetType();
        Integer score = ratingCreateDTO.getScore();

        // 构造评分实体
        Rating rating = Rating.builder()
                .userId(userId)
                .targetId(targetId)
                .targetType(targetType)
                .score(score)
                .build();

        // 插入评分数据
        ratingMapper.insert(rating);

        // 发送消息：收藏数加一
        rabbitTemplate.convertAndSend(
                RabbitConfig.RATING_QUEUE,
                new Message<>(MessageTypeConstant.RATING_COUNT_INCREASE, rating)
        );
    }

    /**
     * 更新评分
     */
    public void update(RatingUpdateDTO ratingUpdateDTO) {
        Rating rating = Rating.builder().score(ratingUpdateDTO.getScore()).build();
        LambdaUpdateWrapper<Rating> updateWrapper = new LambdaUpdateWrapper<Rating>()
                .eq(Rating::getUserId, ThreadUtil.getUserId())
                .eq(Rating::getTargetId, ratingUpdateDTO.getTargetId());
        ratingMapper.update(rating, updateWrapper);
    }

    /**
     * 查询分数计数
     */
    public List<ScoreCountDTO> selectScoreCount(Integer resourceType) {
        return ratingMapper.selectScoreCount(resourceType);
    }

}
