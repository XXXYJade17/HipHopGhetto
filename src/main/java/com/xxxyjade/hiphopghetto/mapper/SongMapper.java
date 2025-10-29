package com.xxxyjade.hiphopghetto.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Song;
import org.apache.ibatis.annotations.Insert;

public interface SongMapper extends BaseMapper<Song> {

    @Insert("insert ignore into song(song_id, song_name, album_id, album_name, singer, duration, cover_url) " +
            "values (#{songId}, #{songName}, #{albumId}, #{albumName}, #{singer}, #{duration}, #{coverUrl})")
    void insertIgnore(Song song);

}
