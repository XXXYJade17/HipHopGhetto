package com.xxxyjade.hiphopghetto.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Album;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface AlbumMapper extends BaseMapper<Album> {

    @Insert("insert ignore into album(id, netease_id, album_name, singer, release_time, cover_url, description, comment_section_id) " +
            "values (#{id}, #{neteaseId}, #{albumName}, #{singer}, #{releaseTime}, #{coverUrl}, #{description}, #{commentSectionId})")
    void insertIgnore(Album album);

    Page<Album> selectAlbumPage(Page<Album> page, @Param(Constants.WRAPPER) QueryWrapper<Album> queryWrapper);

}
