package com.xxxyjade.hiphopghetto.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Song;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface SongMapper extends BaseMapper<Song> {

    @Insert("insert ignore into song(song_id, song_name, album_id, album_name, singer, duration, cover_url) " +
            "values (#{songId}, #{songName}, #{albumId}, #{albumName}, #{singer}, #{duration}, #{coverUrl})")
    void insertIgnore(Song song);

    Page<Song> selectSongPage(Page<Song> page, @Param(Constants.WRAPPER) QueryWrapper<Song> queryWrapper);

}
