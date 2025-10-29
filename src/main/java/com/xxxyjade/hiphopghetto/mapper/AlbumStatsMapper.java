package com.xxxyjade.hiphopghetto.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxyjade.hiphopghetto.common.pojo.entity.AlbumStats;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface AlbumStatsMapper extends BaseMapper<AlbumStats> {

    @Insert("insert ignore into album_stats(album_id) values (#{albumId})")
    void insertIgnore(@Param("albumId") Long albumId);

}
