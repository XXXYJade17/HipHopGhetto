package com.xxxyjade.hiphopghetto.server.like.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.xxxyjade.hiphopghetto.model.dto.LikeDTO;
import com.xxxyjade.hiphopghetto.model.entity.Like;
import com.xxxyjade.hiphopghetto.server.like.mapper.LikeMapper;
import com.xxxyjade.hiphopghetto.server.like.service.LikeMessageService;
import com.xxxyjade.hiphopghetto.server.like.service.LikeService;
import com.xxxyjade.hiphopghetto.util.RedisUtil;
import com.xxxyjade.hiphopghetto.util.ThreadUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeMapper likeMapper;
    private final LikeMessageService likeMessageService;
    private final RedisUtil redisUtil;

    /**
     * 查询点赞状态
     */
    @Cacheable(
            value = "like",
            key = "'like::userId=' + T(com.xxxyjade.hiphopghetto.util.ThreadUtil).getUserId() + '&targetId=' + #likeDTO.targetId",
            unless = "#result == null"
    )
    @Transactional(rollbackFor = Exception.class)
    public Boolean select(Long id) {
        Like like = likeMapper.selectOne(
                new QueryWrapper<Like>()
                        .eq("user_id", ThreadUtil.getUserId())
                        .eq("target_id", id)
        );
        return like != null && like.getIsLiked();
    }

    /**
     * 创建点赞
     */
    @CacheEvict(
            value = "like",
            key = "'like::userId=' + T(com.xxxyjade.hiphopghetto.util.ThreadUtil).getUserId() + '&targetId=' + #likeDTO.targetId"
    )
    @Transactional(rollbackFor = Exception.class)
    public void like(LikeDTO likeDTO) {
        Long userId = ThreadUtil.getUserId();
        Long targetId = likeDTO.getTargetId();

        // 构造实体，用于更新、插入、发送消息
        Like like = Like.builder()
                .userId(userId)
                .targetId(targetId)
                .targetType(likeDTO.getTargetType())
                .build();

        // 尝试更新
        int update = likeMapper.update(
                Like.builder()
                        .isLiked(true)
                        .build(),
                new UpdateWrapper<Like>()
                        .eq("user_id", userId)
                        .eq("target_id", targetId)
        );

        // 若更新记录数小于0，则创建收藏
        if (update < 1) {
            likeMapper.insert(like);
        }

        // 发送消息：收藏数+1
        likeMessageService.sendLikeCountIncreaseMessage(like);
    }

    /**
     * 取消收藏
     */
    @CacheEvict(
            value = "like",
            key = "'like::userId=' + T(com.xxxyjade.hiphopghetto.util.ThreadUtil).getUserId() + '&targetId=' + #likeDTO.targetId"
    )
    @Transactional(rollbackFor = Exception.class)
    public void unlike(LikeDTO likeDTO) {
        Long userId = ThreadUtil.getUserId();
        Long targetId = likeDTO.getTargetId();

        // 构造实体，用于更新、发送消息
        Like like = Like.builder()
                .userId(userId)
                .targetId(targetId)
                .targetType(likeDTO.getTargetType())
                .build();
        // 尝试更新
        int update = likeMapper.update(
                Like.builder()
                        .isLiked(false)
                        .build(),
                new UpdateWrapper<Like>()
                        .eq("user_id", userId)
                        .eq("target_id", targetId)
        );

        // 若影响记录数大于0，即更新成功，则发送消息：收藏数-1
        if (update > 0) {
            likeMessageService.sendLikeCountDecreaseMessage(like);
        }
    }

    /**
     * 批量查询点赞状态
     */
    public Map<Long, Boolean> selectBatch(Long userId, List<Long> ids) {
        List<Long> hasNotCachedIds = new ArrayList<>();
        Map<Long, Boolean> isLikedMap = new HashMap<>();
        String cacheKeyPrefix = "like::userId=" + userId + "&targetId=";
        ids.forEach(id -> {
            Boolean isLiked = (Boolean) redisUtil.get(cacheKeyPrefix + id);
            if (isLiked != null) {
                isLikedMap.put(id, isLiked);
            } else {
                hasNotCachedIds.add(id);
            }
        });
        if (!hasNotCachedIds.isEmpty()) {
            QueryWrapper<Like> wrapper = new QueryWrapper<Like>()
                    .eq("user_id", userId)
                    .in("target_id", hasNotCachedIds);
            List<Like> likes = likeMapper.selectList(wrapper);
            likes.forEach(like -> {
                isLikedMap.put(like.getTargetId(), like.getIsLiked());
                redisUtil.set(cacheKeyPrefix + like.getTargetId(), like.getIsLiked());
            });
        }
        return isLikedMap;
    }

}
