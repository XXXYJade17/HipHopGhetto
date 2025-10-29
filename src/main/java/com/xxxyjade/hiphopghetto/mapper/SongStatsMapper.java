package com.xxxyjade.hiphopghetto.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxyjade.hiphopghetto.common.pojo.entity.SongStats;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface SongStatsMapper extends BaseMapper<SongStats> {

    @Insert("insert ignore into song_stats(song_id) values (#{songId})")
    void insertIgnore(@Param("songId") Long songId);

}
