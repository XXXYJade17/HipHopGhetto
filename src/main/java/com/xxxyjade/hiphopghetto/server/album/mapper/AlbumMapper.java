package com.xxxyjade.hiphopghetto.server.album.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxyjade.hiphopghetto.model.entity.Album;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;

public interface AlbumMapper extends BaseMapper<Album> {

    @Insert("insert ignore into album(id, netease_id, album_name, artists, release_time, cover_url, description) " +
            "values (#{id}, #{neteaseId}, #{albumName}, #{artists}, #{releaseTime}, #{coverUrl}, #{description})")
    int insertIgnore(Album album);

    @Update("update album " +
            "set collection_count = collection_count + 1 " +
            "where id = #{id}")
    int increaseCollectionCount(Long id);

    @Update("update album " +
            "set collection_count = collection_count - 1 " +
            "where id = #{id}")
    int decreaseCollectionCount(Long id);

    @Update("update album " +
            "set rating_count = rating_count + 1 " +
            "where id = #{id}")
    int increaseRatingCount(Long id);

    @Update("update album " +
            "set rating_count = rating_count - 1 " +
            "where id = #{id}")
    int decreaseRatingCount(Long id);

}
