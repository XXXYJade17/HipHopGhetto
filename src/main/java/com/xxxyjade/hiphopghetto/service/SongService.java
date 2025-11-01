package com.xxxyjade.hiphopghetto.service;

import com.xxxyjade.hiphopghetto.common.pojo.dto.PageQueryDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.SongScoreDTO;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Song;
import com.xxxyjade.hiphopghetto.common.pojo.vo.SongScoreVO;
import com.xxxyjade.hiphopghetto.common.pojo.vo.SongInfoVO;

import java.util.List;

public interface SongService {

    /**
     * 插入歌曲数据
     */
    void insert(Song song);

    /**
     * 分页查询
     * @param pageQueryDTO 分页查询DTO
     * @return 分页VO
     */
    List<Song> page(PageQueryDTO pageQueryDTO);

    /**
     * 查询详情
     */
    SongInfoVO info(Long id);

    /**
     * 歌曲评分
     * @param songScoreDTO 歌曲评分DTO
     */
    void score(SongScoreDTO songScoreDTO);

    /**
     * 是否已经评分
     */
    Integer hasScore(Long songId);

}
