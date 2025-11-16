package com.xxxyjade.hiphopghetto.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxyjade.hiphopghetto.common.pojo.dto.ScoreCountDTO;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Rating;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RatingMapper extends BaseMapper<Rating> {

    @Insert("insert into rating (id, user_id, target_id, target_type, score) " +
            "values (#{id}, #{userId}, #{targetId}, #{targetType}, #{score}) " +
            "on duplicate key update " +
            "score = #{score}")
    void insertOrUpdateRating(Rating rating);

    @Select("select resource_id, score, count(*) as score_count from score " +
            "where resource_type = #{resourceType} " +
            "group by resource_id, score")
    List<ScoreCountDTO> selectScoreCount(@Param("resourceType") Integer resourceType);

}
