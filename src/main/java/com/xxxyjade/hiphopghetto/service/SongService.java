package com.xxxyjade.hiphopghetto.service;

import com.xxxyjade.hiphopghetto.common.pojo.dto.PageQueryDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.ScoreDTO;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Comment;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Song;
import com.xxxyjade.hiphopghetto.common.pojo.vo.PageVO;
import com.xxxyjade.hiphopghetto.common.pojo.vo.SongInfoVO;


public interface SongService {

    /**
     * 插入歌曲数据
     */
    void insert(Song song);

    /**
     * （条件）分页查询
     */
    PageVO<Song> page(PageQueryDTO pageQueryDTO);

    /**
     * 查询详情
     */
    SongInfoVO info(Long id);

    /**
     * 创建/修改歌曲评分
     */
    void score(ScoreDTO scoreDTO);

    /**
     * 收藏/取消收藏专辑
     */
    void collect(Long id);

}
