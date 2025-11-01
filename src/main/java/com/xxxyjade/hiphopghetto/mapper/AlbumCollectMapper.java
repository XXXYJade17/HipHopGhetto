package com.xxxyjade.hiphopghetto.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxyjade.hiphopghetto.common.pojo.entity.AlbumCollect;
import org.apache.ibatis.annotations.Insert;

public interface AlbumCollectMapper extends BaseMapper<AlbumCollect> {

    Boolean selectOrInsert(Long userId, Long albumId);

    @Insert("insert into album_collect (user_id, album_id, collect)" +
            "values (#{userId}, #{albumId}, 1)" +
            "on duplicate key update collect = not collect;")
    void collect(Long userId, Long albumId);

}
