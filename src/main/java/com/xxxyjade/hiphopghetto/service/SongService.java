package com.xxxyjade.hiphopghetto.service;

import com.xxxyjade.hiphopghetto.common.pojo.dto.PageQueryDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.SongScoreDTO;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Song;
import com.xxxyjade.hiphopghetto.common.pojo.vo.PageVO;
import com.xxxyjade.hiphopghetto.common.pojo.vo.SongScoreVO;
import com.xxxyjade.hiphopghetto.common.pojo.vo.SongVO;

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
    PageVO<Song> page(PageQueryDTO pageQueryDTO);

    /**
     * 查询详情
     * @param id 歌曲Id
     * @return 歌曲VO
     */
    SongVO info(Long id);

    /**
     * 歌曲评分
     * @param songScoreDTO 歌曲评分DTO
     */
    void score(SongScoreDTO songScoreDTO);

    /**
     * 获取评分
     * @param id 歌曲id
     * @return 歌曲评分VO
     */
    SongScoreVO getScore(Long id);

}
