package com.xxxyjade.hiphopghetto.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxyjade.hiphopghetto.common.constant.Number;
import com.xxxyjade.hiphopghetto.common.pojo.dto.VotingCreateDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.VotingDTO;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Activity;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Image;
import com.xxxyjade.hiphopghetto.mapper.VotingMapper;
import com.xxxyjade.hiphopghetto.mapper.VotingImageMapper;
import com.xxxyjade.hiphopghetto.mapper.VotingScoreMapper;
import com.xxxyjade.hiphopghetto.service.VotingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class VotingServiceImpl extends ServiceImpl<VotingMapper, Activity> implements VotingService {

    @Autowired
    private VotingMapper votingMapper;
    @Autowired
    private VotingImageMapper votingImageMapper;
    @Autowired
    private VotingScoreMapper scoreMapper;

    /**
     * 创建活动
     * @param votingCreateDTO 活动创建DTO
     */
    @Transactional(rollbackFor = Exception.class)
    public void create(VotingCreateDTO votingCreateDTO) {
        Activity activity = Activity.builder()
                .userId(votingCreateDTO.getUserId())
                .title(votingCreateDTO.getTitle())
                .description(votingCreateDTO.getDescription())
                .build();
        votingMapper.insert(activity);

        List<String> images = votingCreateDTO.getImages();

        if (images == null || images.isEmpty()) {
            return;
        }

        images.forEach((String url)->{
            Image image = Image.builder()
                    .activityId(activity.getId())
                    .url(url)
                    .build();
            votingImageMapper.insert(image);
        });

    }

    /**
     * 打分
     * @param votingDTO 活动打分DTO
     */
    public void vote(VotingDTO votingDTO) {
        Long activityId = votingDTO.getActivityId();
        String num = Number.getStr(votingDTO.getScore());
        scoreMapper.vote(activityId, num);
    }
}
