package com.xxxyjade.hiphopghetto.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxyjade.hiphopghetto.common.pojo.entity.ScoreCount;
import com.xxxyjade.hiphopghetto.common.pojo.entity.AlbumScore;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AlbumScoreMapper extends BaseMapper<AlbumScore> {

    @Select("select album_id as id, score, count(*) as count from album_score group by album_id, score")
    List<ScoreCount> getScoreCount();

    @Select("select score from album_score where user_id = #{userId} and album_id = #{albumId}")
    Integer select(@Param("userId") Long userId, @Param("albumId") Long albumId);

}
