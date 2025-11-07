package com.xxxyjade.hiphopghetto.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Like;
import com.xxxyjade.hiphopghetto.mapper.LikeMapper;
import com.xxxyjade.hiphopghetto.service.LikeService;
import com.xxxyjade.hiphopghetto.util.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LikeServiceImpl implements LikeService {

    @Autowired
    private LikeMapper likeMapper;

    /**
     * 是否点赞
     */
    public Boolean select(Long resourceId) {
        return likeMapper.exists(
                new QueryWrapper<Like>()
                        .eq("user_id", ThreadUtil.getUserId())
                        .eq("resource_id", resourceId)
        );
    }

    /**
     * 点赞
     */
    public void like(Long resourceId) {
        likeMapper.insert(
                Like.builder()
                        .userId(ThreadUtil.getUserId())
                        .resourceId(resourceId)
                        .build()
        );
    }

    /**
     * 取消点赞
     */
    public void cancel(Long resourceId) {
        likeMapper.delete(
                new QueryWrapper<Like>()
                        .eq("user_id", ThreadUtil.getUserId())
                        .eq("resource_id", resourceId)
        );
    }

}
