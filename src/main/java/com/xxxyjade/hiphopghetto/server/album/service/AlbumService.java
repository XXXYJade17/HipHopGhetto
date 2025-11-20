package com.xxxyjade.hiphopghetto.server.album.service;

import com.xxxyjade.hiphopghetto.model.dto.PageQueryDTO;
import com.xxxyjade.hiphopghetto.model.entity.Album;
import com.xxxyjade.hiphopghetto.model.vo.AlbumDetailVO;
import com.xxxyjade.hiphopghetto.model.vo.PageVO;

public interface AlbumService {

    /**
     * （条件）分页查询
     */
    PageVO<Album> page(PageQueryDTO pageQueryDTO);

    /**
     * 查询专辑详情
     */
    AlbumDetailVO detail(Long id);

    /**
     * 插入专辑，若存在则忽略
     */
    void insertIgnore(Album album);

    /**
     * 收藏数递增
     */
    void increaseCollectionCount(Long id);

    /**
     * 收藏数递减
     */
    void decreaseCollectionCount(Long id);

    /**
     * 评分数递增
     */
    void increaseRatingCount(Long id);

}
