package com.xxxyjade.hiphopghetto.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Album;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface AlbumMapper extends BaseMapper<Album> {

    @Insert("insert ignore into album(album_id, album_name, singer, release_time, cover_url, description) " +
            "values (#{albumId}, #{albumName}, #{singer}, #{releaseTime}, #{coverUrl}, #{description})")
    void insertIgnore(Album album);

    Page<Album> selectAlbumPage(Page<Album> page, @Param(Constants.WRAPPER) QueryWrapper<Album> queryWrapper);

}
