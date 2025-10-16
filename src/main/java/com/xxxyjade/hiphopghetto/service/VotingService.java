package com.xxxyjade.hiphopghetto.service;

import com.xxxyjade.hiphopghetto.common.pojo.dto.VotingCreateDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.VotingDTO;

public interface VotingService {

    /**
     * 创建活动
     * @param votingCreateDTO 活动创建DTO
     */
    void create(VotingCreateDTO votingCreateDTO);

    /**
     * 打分
     * @param votingDTO 活动打分DTO
     */
    void vote(VotingDTO votingDTO);


}
