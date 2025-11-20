package com.xxxyjade.hiphopghetto.server.rating.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xxxyjade.hiphopghetto.model.dto.RatingDTO;
import com.xxxyjade.hiphopghetto.model.entity.Rating;
import com.xxxyjade.hiphopghetto.server.rating.mapper.RatingMapper;
import com.xxxyjade.hiphopghetto.server.rating.service.RatingMessageService;
import com.xxxyjade.hiphopghetto.server.rating.service.RatingService;
import com.xxxyjade.hiphopghetto.util.ThreadUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingMapper ratingMapper;
    private final RatingMessageService ratingMessageService;

    /**
     * 查询用户评分
     */
    @Cacheable(
            value = "rating",
            key = "'rating::userId=' + T(com.xxxyjade.hiphopghetto.util.ThreadUtil).getUserId() + '&targetId=' + #targetId",
            unless = "#result == null"
    )
    @Transactional(rollbackFor = Exception.class)
    public Integer select(Long targetId) {
        Long userId = ThreadUtil.getUserId();
        QueryWrapper<Rating> wrapper = new QueryWrapper<Rating>()
                .eq("user_id", userId)
                .eq("target_id", targetId);

        Rating rating = ratingMapper.selectOne(wrapper);
        return rating == null ? -1 : rating.getScore();
    }

    /**
     * 评分
     */
    @CacheEvict(value = "rating", key = "'rating::userId=' + T(com.xxxyjade.hiphopghetto.util.ThreadUtil).getUserId() + '&targetId=' + #ratingDTO.targetId")
    @Transactional(rollbackFor = Exception.class)
    public void rate(RatingDTO ratingDTO) {
        Rating rating = Rating.builder()
                .userId(ThreadUtil.getUserId())
                .targetId(ratingDTO.getTargetId())
                .targetType(ratingDTO.getTargetType())
                .score(ratingDTO.getScore())
                .build();

        // 尝试更新评分
        int update = ratingMapper.update(rating, null);

        // 若更新记录数小于1，即数据不存在，则创建评分
        if (update < 1) {
            ratingMapper.insert(rating);
        }
        // 发送消息：评分数+1
        ratingMessageService.sendRatingCountIncreaseMessage(rating);
    }

}
