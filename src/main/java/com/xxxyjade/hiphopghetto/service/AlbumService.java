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
     * @param id 专辑Id
     * @return 专辑VO
     */
    AlbumInfoVO info(Long id);

    /**
     * 专辑评分
     * @param albumScoreDTO 歌曲评分DTO
     */
    void score(AlbumScoreDTO albumScoreDTO);

    /**
     * 有无评分记录
     * @param albumId 专辑 id
     * @return null:没有记录  Integer:评分
     */
    Integer hasScore(Long albumId);

}
