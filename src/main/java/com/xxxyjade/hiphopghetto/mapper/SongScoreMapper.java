package com.xxxyjade.hiphopghetto.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxyjade.hiphopghetto.common.pojo.entity.ScoreCount;
import com.xxxyjade.hiphopghetto.common.pojo.entity.SongScore;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SongScoreMapper extends BaseMapper<SongScore> {

    @Select("select song_id as id, score, count(*) as count from song_score group by song_id, score")
    List<ScoreCount> getScoreCount();

    @Select("select score from song_score where user_id = #{userId} and song_id = #{songId}")
    String select(@Param("userId") Long userId, @Param("songId") Long songId);

}
