package com.xxxyjade.hiphopghetto.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxyjade.hiphopghetto.common.pojo.dto.ScoreCountDTO;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Score;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ScoreMapper extends BaseMapper<Score> {

    @Insert("insert into score (user_id, resource_id, score) " +
            "values (#{userId}, #{resourceId}, #{score}) " +
            "on duplicate key update " +
            "score = #{score}")
    void insertOrUpdateScore(Score score);

    @Select("select resource_id, score, count(*) as score_count from score " +
            "where resource_type = #{resourceType} " +
            "group by resource_id, score")
    List<ScoreCountDTO> selectScoreCount(@Param("resourceType") Integer resourceType);

}
