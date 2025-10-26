package com.xxxyjade.hiphopghetto.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Album;
import org.apache.ibatis.annotations.Insert;

public interface AlbumMapper extends BaseMapper<Album> {

    @Insert("insert ignore into album(album_id, album_name, singer, release_time, cover_url, description) values (#{album_id}, #{album_name}, #{singer}, #{release_time}, #{cover_url}, #{description})")
    void insertIgnore(Album album);

}
