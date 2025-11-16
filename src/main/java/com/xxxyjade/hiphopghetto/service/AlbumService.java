package com.xxxyjade.hiphopghetto.service;

import com.xxxyjade.hiphopghetto.common.pojo.dto.PageQueryDTO;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Album;
import com.xxxyjade.hiphopghetto.common.pojo.vo.AlbumInfoVO;
import com.xxxyjade.hiphopghetto.common.pojo.vo.PageVO;

import java.util.List;

public interface AlbumService {

    /**
     * （条件）分页查询
     */
    PageVO<Album> page(PageQueryDTO pageQueryDTO);

    /**
     * 查询专辑详情
     */
    AlbumInfoVO info(Long id);

    /**
     * 更新专辑信息
     */
    void update(List<Album> albums);

    /**
     * 插入专辑，若存在则忽略
     */
    void insertIgnore(Album album);

    /**
     * 增加累计评分
     */
    void increaseRatingCount(Long id);
}
