package com.xxxyjade.hiphopghetto.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxyjade.hiphopghetto.common.pojo.entity.SongScoreSummary;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface SongScoreSummaryMapper extends BaseMapper<SongScoreSummary> {

    @Insert("insert ignore into song_score_summary(song_id) values (#{songId})")
    void insertIgnore(@Param("songId") Long songId);

}
