package com.xxxyjade.hiphopghetto.service;

import com.xxxyjade.hiphopghetto.common.pojo.entity.Album;

import java.util.List;

public interface StatsService {

    /**
     * 查询专辑聚合数据
     */
    List<Album> selectAlbumStats();

}
