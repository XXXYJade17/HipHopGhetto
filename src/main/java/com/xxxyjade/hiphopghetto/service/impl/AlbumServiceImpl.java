package com.xxxyjade.hiphopghetto.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxxyjade.hiphopghetto.common.constant.Number;
import com.xxxyjade.hiphopghetto.common.pojo.dto.AlbumScoreDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.PageQueryDTO;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Album;
import com.xxxyjade.hiphopghetto.common.pojo.entity.AlbumScore;
import com.xxxyjade.hiphopghetto.common.pojo.entity.AlbumStats;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Song;
import com.xxxyjade.hiphopghetto.common.pojo.vo.*;
import com.xxxyjade.hiphopghetto.mapper.*;
import com.xxxyjade.hiphopghetto.service.AlbumService;
import com.xxxyjade.hiphopghetto.util.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    private AlbumMapper albumMapper;
    @Autowired
    private AlbumStatsMapper albumStatsMapper;
    @Autowired
    private AlbumScoreMapper albumScoreMapper;
    @Autowired
    private AlbumCollectMapper albumCollectMapper;
    @Autowired
    private SongMapper songMapper;

    /**
     * 插入数据
     */
    @Transactional(rollbackFor = Exception.class)
    public void insert(Album album) {
        albumMapper.insertIgnore(album);
        albumStatsMapper.insertIgnore(album.getAlbumId());
    }

    /**
     * （条件）分页查询
     */
    public List<Album> page(PageQueryDTO pageQueryDTO) {
        QueryWrapper<Album> wrapper = new QueryWrapper<>();
        String clause = "";
        switch (pageQueryDTO.getSortType()){
            case AVG_SCORE -> clause = "avg_score";
            case SCORE_COUNT -> clause = "score_count";
            case COLLECT_COUNT -> clause = "collect_count";
            case COMMENT_COUNT -> clause = "comment_count";
        }
        if (!clause.isEmpty()) {
            wrapper = wrapper.orderByDesc("album_stats." + clause);
        }
        Page<Album> page = new Page<>(pageQueryDTO.getPage(), pageQueryDTO.getSize());
        page = albumMapper.selectAlbumPage(page, wrapper);
        return page.getRecords();
    }

    /**
     * 查询详情
     */
    @Transactional(rollbackFor = Exception.class)
    public AlbumInfoVO info(Long id) {
        Album album = albumMapper.selectById(id);
        AlbumStats albumStats = albumStatsMapper.selectById(id);    // 专辑聚合数据
        // 专辑下歌曲数据
        List<Song> songs = songMapper.selectList(new QueryWrapper<Song>().eq("album_id", id));
        AlbumInfoVO albumInfoVO = new AlbumInfoVO();
        BeanUtils.copyProperties(album, albumInfoVO);
        albumInfoVO.setAlbumStats(albumStats);
        albumInfoVO.setSongs(songs);
        System.out.println(albumStats.getScoreCount());
        System.out.println(albumStats.getNine());
        return albumInfoVO;
    }

    /**
     * 歌曲评分
     */
    public void score(AlbumScoreDTO albumScoreDTO) {
        Long userId = ThreadUtil.getId();
        AlbumScore albumScore = AlbumScore.builder()
                .albumId(albumScoreDTO.getAlbumId())
                .userId(userId)
                .score(Number.getStr(albumScoreDTO.getScore()))
                .build();
        BeanUtils.copyProperties(albumScoreDTO, albumScore);
        albumScoreMapper.insertOrUpdate(albumScore);
    }

    /**
     * 有无评分记录
     */
    public Integer hasScore(Long albumId) {
        Long userId = ThreadUtil.getId();
        return Number.getInt(albumScoreMapper.select(userId, albumId));
    }

}
