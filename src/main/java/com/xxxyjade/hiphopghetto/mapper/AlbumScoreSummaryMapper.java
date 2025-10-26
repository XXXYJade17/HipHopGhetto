package com.xxxyjade.hiphopghetto.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxyjade.hiphopghetto.common.pojo.entity.AlbumScoreSummary;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface AlbumScoreSummaryMapper extends BaseMapper<AlbumScoreSummary> {

    @Insert("insert ignore into album_score_summary(album_id) values (#{albumId})")
    void insertIgnore(@Param("albumId") Long albumId);

}
