package com.xxxyjade.hiphopghetto.service;

import com.xxxyjade.hiphopghetto.common.pojo.dto.ScoreDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.CommentDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.PageQueryDTO;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Album;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Comment;
import com.xxxyjade.hiphopghetto.common.pojo.vo.AlbumInfoVO;
import com.xxxyjade.hiphopghetto.common.pojo.vo.PageVO;

public interface AlbumService {

    /**
     * 插入数据
     */
    void insert(Album album);

    /**
     * （条件）分页查询
     */
    PageVO<Album> page(PageQueryDTO pageQueryDTO);

    /**
     * 查询专辑详情
     */
    AlbumInfoVO info(Long id);

    /**
     * 创建/修改专辑评分
     */
    void score(ScoreDTO scoreDTO);

    /**
     * 收藏/取消收藏专辑
     */
    void collect(Long id);

}
