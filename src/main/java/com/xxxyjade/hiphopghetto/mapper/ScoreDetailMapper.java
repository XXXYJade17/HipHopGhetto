package com.xxxyjade.hiphopghetto.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxyjade.hiphopghetto.common.pojo.entity.ScoreDetail;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface ScoreDetailMapper extends BaseMapper<ScoreDetail> {

    @Update("update score_detail SET ${num} = ${num} + 1 WHERE score_id = #{scoreId}")
    void vote(@Param("scoreId") Long scoreId, @Param("num") String num);
}
