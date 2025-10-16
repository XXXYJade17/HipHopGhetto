package com.xxxyjade.hiphopghetto.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Score;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface VotingScoreMapper extends BaseMapper<Score> {

    @Update("update activity_score SET ${num} = ${num} + 1 WHERE activity_id = #{activityId}")
    void vote(@Param("activityId") Long activityId, @Param("num") String num);
}
