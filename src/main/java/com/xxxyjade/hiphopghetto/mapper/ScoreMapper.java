package com.xxxyjade.hiphopghetto.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Score;
import org.apache.ibatis.annotations.Select;

public interface ScoreMapper extends BaseMapper<Score> {

    @Select("select score from score where user_id = #{userId} and resource_id = #{resourceId}")
    Integer isScored(Long userId, Long resourceId);

}
