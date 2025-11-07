package com.xxxyjade.hiphopghetto.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Collect;
import com.xxxyjade.hiphopghetto.mapper.CollectMapper;
import com.xxxyjade.hiphopghetto.service.CollectService;
import com.xxxyjade.hiphopghetto.util.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CollectServiceImpl implements CollectService {

    @Autowired
    private CollectMapper collectMapper;

    /**
     * 是否收藏
     */
    public Boolean select(Long resourceId) {
        return collectMapper.exists(
                new QueryWrapper<Collect>()
                        .eq("user_id", ThreadUtil.getUserId())
                        .eq("resource_id", resourceId)
        );
    }

    /**
     * 收藏
     */
    public void collect(Long resourceId) {
        collectMapper.insert(
                Collect.builder()
                        .userId(ThreadUtil.getUserId())
                        .resourceId(resourceId)
                        .build()
        );
    }

    /**
     * 取消收藏
     */
    public void cancel(Long resourceId) {
        collectMapper.delete(
                new QueryWrapper<Collect>()
                        .eq("user_id", ThreadUtil.getUserId())
                        .eq("resource_id", resourceId)
        );
    }

}
