package com.xxxyjade.hiphopghetto.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxxyjade.hiphopghetto.common.constant.Number;
import com.xxxyjade.hiphopghetto.common.pojo.dto.PageQueryDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.SongScoreDTO;
import com.xxxyjade.hiphopghetto.common.pojo.entity.*;
import com.xxxyjade.hiphopghetto.common.pojo.vo.SongScoreVO;
import com.xxxyjade.hiphopghetto.common.pojo.vo.SongInfoVO;
import com.xxxyjade.hiphopghetto.mapper.SongMapper;
import com.xxxyjade.hiphopghetto.mapper.SongScoreMapper;
import com.xxxyjade.hiphopghetto.mapper.SongStatsMapper;
import com.xxxyjade.hiphopghetto.service.SongService;
import com.xxxyjade.hiphopghetto.util.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SongServiceImpl implements SongService {

    @Autowired
    private SongMapper songMapper;
    @Autowired
    private SongScoreMapper songScoreMapper;
    @Autowired
    private SongStatsMapper songStatsMapper;

    /**
     * 插入歌曲数据
     */
    public void insert(Song song) {
        songMapper.insertIgnore(song);
        songStatsMapper.insertIgnore(song.getSongId());
    }

    /**
     * （条件）分页查询
     */
    public List<Song> page(PageQueryDTO pageQueryDTO) {
        QueryWrapper<Song> wrapper = new QueryWrapper<>();
        String clause = "";
        switch (pageQueryDTO.getSortType()){
            case AVG_SCORE -> clause = "avg_score";
            case COLLECT_COUNT -> clause = "collect_count";
            case COMMENT_COUNT -> clause = "comment_count";
        }
        if (!clause.isEmpty()) {
            wrapper = wrapper.orderByDesc("song_stats." + clause);
        }
        Page<Song> page = new Page<>(pageQueryDTO.getPage(), pageQueryDTO.getSize());
        page = songMapper.selectSongPage(page, wrapper);
        return page.getRecords();
    }

    /**
     * 查询详情
     */
    public SongInfoVO info(Long id) {
        Song song = songMapper.selectById(id);
        SongStats songStats = songStatsMapper.selectById(id);

        SongInfoVO songInfoVO = new SongInfoVO();
        BeanUtils.copyProperties(song, songInfoVO);
        BeanUtils.copyProperties(songStats, songInfoVO);
        System.out.println(songInfoVO);
        return songInfoVO;
    }

    /**
     * 歌曲评分
     */
    public void score(SongScoreDTO songScoreDTO) {
        Long userId = ThreadUtil.getId();
        SongScore songScore = SongScore.builder()
                .songId(songScoreDTO.getSongId())
                .userId(userId)
                .score(Number.getStr(songScoreDTO.getScore()))
                .build();
        songScoreMapper.insertOrUpdate(songScore);
    }

    /**
     * 是否已经评分
     */
    public Integer hasScore(Long songId) {
        Long userId = ThreadUtil.getId();
        return Number.getInt(songScoreMapper.select(userId, songId));
    }

}
