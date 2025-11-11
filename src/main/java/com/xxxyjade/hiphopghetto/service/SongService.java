package com.xxxyjade.hiphopghetto.service;

import com.xxxyjade.hiphopghetto.common.pojo.dto.PageQueryDTO;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Song;
import com.xxxyjade.hiphopghetto.common.pojo.vo.PageVO;
import com.xxxyjade.hiphopghetto.common.pojo.vo.SongInfoVO;

import java.util.List;


public interface SongService {

    /**
     * （条件）分页查询
     */
    PageVO<Song> page(PageQueryDTO pageQueryDTO);

    /**
     * 查询详情
     */
    SongInfoVO info(Long id);

    /**
     * 查询专辑中全部歌曲
     */
    List<Song> selectByAlbumId(Long albumId);

    /**
     * 处理平均分
     */
    void processAvgScore();
}
