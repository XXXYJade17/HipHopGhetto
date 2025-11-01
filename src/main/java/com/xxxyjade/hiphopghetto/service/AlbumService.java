package com.xxxyjade.hiphopghetto.service;

import com.xxxyjade.hiphopghetto.common.pojo.dto.AlbumScoreDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.PageQueryDTO;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Album;
import com.xxxyjade.hiphopghetto.common.pojo.vo.AlbumInfoVO;

import java.util.List;

public interface AlbumService {

    /**
     * 插入专辑数据
     */
    void insert(Album album);

    /**
     * 分页查询
     */
    List<Album> page(PageQueryDTO pageQueryDTO);

    /**
     * 查询详情
     */
    AlbumInfoVO info(Long albumId);

    /**
     * 专辑评分
     */
    void score(AlbumScoreDTO albumScoreDTO);

    /**
     * 收藏/取消专辑
     */
    void collect(Long albumId);

}
