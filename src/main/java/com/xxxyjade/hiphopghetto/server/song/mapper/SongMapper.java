package com.xxxyjade.hiphopghetto.server.song.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxyjade.hiphopghetto.model.entity.Song;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;

public interface SongMapper extends BaseMapper<Song> {

    @Insert("insert ignore into song(id, netease_id, song_name, album_id, album_name, artists, release_time, duration, cover_url) " +
            "values (#{id}, #{neteaseId}, #{songName}, #{albumId}, #{albumName}, #{artists}, #{releaseTime}, #{duration}, #{coverUrl})")
    int insertIgnore(Song song);

    @Update("update song " +
            "set collection_count = collection_count + 1 " +
            "where id = #{id}")
    int increaseCollectionCount(Long id);

    @Update("update song " +
            "set collection_count = collection_count - 1 " +
            "where id = #{id}")
    int decreaseCollectionCount(Long id);

    @Update("update song " +
            "set rating_count = rating_count + 1 " +
            "where id = #{id}")
    int increaseRatingCount(Long id);

    @Update("update song " +
            "set rating_count = rating_count - 1 " +
            "where id = #{id}")
    int decreaseRatingCount(Long id);

}
