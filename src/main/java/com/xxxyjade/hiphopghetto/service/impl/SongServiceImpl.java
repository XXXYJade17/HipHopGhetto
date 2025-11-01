package com.xxxyjade.hiphopghetto.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxxyjade.hiphopghetto.common.pojo.dto.PageQueryDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.SongScoreDTO;
import com.xxxyjade.hiphopghetto.common.pojo.entity.*;
import com.xxxyjade.hiphopghetto.common.pojo.vo.SongScoreVO;
import com.xxxyjade.hiphopghetto.common.pojo.vo.SongVO;
import com.xxxyjade.hiphopghetto.mapper.SongMapper;
import com.xxxyjade.hiphopghetto.mapper.SongScoreMapper;
import com.xxxyjade.hiphopghetto.mapper.SongStatsMapper;
import com.xxxyjade.hiphopghetto.service.SongService;
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
            case SCORE_COUNT -> clause = "score_count";
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
     * @param id 歌曲Id
     * @return 歌曲VO
     */
    public SongVO info(Long id) {
        Song song = songMapper.selectById(id);
        SongVO songVO = new SongVO();
        BeanUtils.copyProperties(song, songVO);
        return songVO;
    }

    /**
     * 歌曲评分
     * @param songScoreDTO 歌曲评分DTO
     */
    public void score(SongScoreDTO songScoreDTO) {
        SongScore songScore = new SongScore();
        BeanUtils.copyProperties(songScoreDTO, songScore);
        songScoreMapper.insert(songScore);
    }

    /**
     * 获取评分
     * @param id 歌曲id
     * @return 歌曲评分VO
     */
    public SongScoreVO getScore(Long id) {
//        SongScoreSummary songScoreSummary = songStatsMapper.selectById(id);
//        // 如果汇总表不存在
//        if (songScoreSummary == null) {
//            SongScoreSummary insert = SongScoreSummary.builder()
//                    .id(id)
//                    .build();
//            songStatsMapper.insert(insert);
//            songScoreSummary = songStatsMapper.selectById(id);
//        }
//        SongScoreVO songScoreVO = new SongScoreVO();
//        BeanUtils.copyProperties(songScoreSummary, songScoreVO);
//        return songScoreVO;
        return null;
    }

}
