package com.xxxyjade.hiphopghetto.server.collection.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.xxxyjade.hiphopghetto.model.dto.CollectionDTO;
import com.xxxyjade.hiphopghetto.model.entity.Collection;
import com.xxxyjade.hiphopghetto.server.collection.mapper.CollectionMapper;
import com.xxxyjade.hiphopghetto.server.collection.service.CollectionMessageService;
import com.xxxyjade.hiphopghetto.server.collection.service.CollectionService;
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
public class CollectionServiceImpl implements CollectionService {

    private final CollectionMapper collectionMapper;
    private final CollectionMessageService collectionMessageService;

    /**
     * 查询收藏状态
     */
    @Cacheable(
            value = "collection",
            key = "'isCollected::userId=' + T(com.xxxyjade.hiphopghetto.util.ThreadUtil).getUserId() + '&targetId=' + #targetId",
            unless = "#result == null"
    )
    @Transactional(rollbackFor = Exception.class)
    public Boolean select(Long targetId) {
        Collection collection = collectionMapper.selectOne(
                new QueryWrapper<Collection>()
                        .eq("user_id", ThreadUtil.getUserId())
                        .eq("target_id", targetId)
        );
        return collection != null && collection.getIsCollected();
    }

    /**
     * 创建收藏
     */
    @CacheEvict(
            value = "collection",
            key = "'isCollected::userId=' + T(com.xxxyjade.hiphopghetto.util.ThreadUtil).getUserId() + '&targetId=' + #collectionDTO.targetId"
    )
    @Transactional(rollbackFor = Exception.class)
    public void collect(CollectionDTO collectionDTO) {
        Long userId = ThreadUtil.getUserId();
        Long targetId = collectionDTO.getTargetId();

        // 构造实体，用于更新、插入、发送消息
        Collection collection = Collection.builder()
                .userId(userId)
                .targetId(targetId)
                .targetType(collectionDTO.getTargetType())
                .build();

        // 尝试更新
        int update = collectionMapper.update(
                Collection.builder()
                        .isCollected(true)
                        .build(),
                new UpdateWrapper<Collection>()
                        .eq("user_id", userId)
                        .eq("target_id", targetId)
        );

        // 若更新记录数小于0，则创建收藏
        if (update < 1) {
            collectionMapper.insert(collection);
        }

        // 发送消息：收藏数递增
        collectionMessageService.sendCollectionCountIncreaseMessage(collection);
    }

    /**
     * 取消收藏
     */
    @CacheEvict(
            value = "collection",
            key = "'isCollected::userId=' + T(com.xxxyjade.hiphopghetto.util.ThreadUtil).getUserId() + '&targetId=' + #collectionDTO.targetId"
    )
    @Transactional(rollbackFor = Exception.class)
    public void uncollect(CollectionDTO collectionDTO) {
        Long userId = ThreadUtil.getUserId();
        Long targetId = collectionDTO.getTargetId();

        // 构造实体，用于更新、发送消息
        Collection collection = Collection.builder()
                .userId(userId)
                .targetId(targetId)
                .targetType(collectionDTO.getTargetType())
                .build();
        // 尝试更新
        int update = collectionMapper.update(
                Collection.builder()
                        .isCollected(false)
                        .build(),
                new UpdateWrapper<Collection>()
                        .eq("user_id", userId)
                        .eq("target_id", targetId)
        );

        // 若影响记录数大于0，即更新成功，则发送消息：收藏数递减
        if (update > 0) {
            collectionMessageService.sendCollectionCountDecreaseMessage(collection);
        }
    }

}
