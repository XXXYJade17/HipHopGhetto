package com.xxxyjade.hiphopghetto.service.impl;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxxyjade.hiphopghetto.common.constant.ResourceType;
import com.xxxyjade.hiphopghetto.common.enums.SortType;
import com.xxxyjade.hiphopghetto.common.pojo.dto.PageQueryDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.ScoreCountDTO;
import com.xxxyjade.hiphopghetto.common.pojo.entity.*;
import com.xxxyjade.hiphopghetto.common.pojo.vo.*;
import com.xxxyjade.hiphopghetto.mapper.*;
import com.xxxyjade.hiphopghetto.service.AlbumService;
import com.xxxyjade.hiphopghetto.service.CollectService;
import com.xxxyjade.hiphopghetto.service.ScoreService;
import com.xxxyjade.hiphopghetto.service.SongService;
import com.xxxyjade.hiphopghetto.util.KeyGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    private AlbumMapper albumMapper;
    @Autowired
    private CollectService collectService;
    @Autowired
    @Lazy
    private ScoreService scoreService;
    @Autowired
    private SongService songService;

    /**
     * （条件）分页查询专辑
     */
    @Cacheable(
            value = "albumPage",
            key = "'albumPage::page=' + #pageQueryDTO.page + '&size=' + #pageQueryDTO.size + '&sort=' + #pageQueryDTO.sortType",
            unless = "#result == null"
    )
    @Transactional(rollbackFor = Exception.class)
    public PageVO<Album> page(PageQueryDTO pageQueryDTO) {
        SortType sortType = pageQueryDTO.getSortType();
        Page<Album> page = new Page<>(pageQueryDTO.getPage(), pageQueryDTO.getSize());
        if (sortType != SortType.DEFAULT) {
            page.setOrders(Collections.singletonList(OrderItem.desc(sortType.getType())));
        }
        albumMapper.selectPage(page, null);
        return new PageVO<>(page.getTotal(), page.getRecords());
    }

    /**
     * 查询专辑详情
     */
    @Cacheable(
            value ="albumInfo",
            key = "'albumInfo::id=' + #id",
            unless ="#result == null"
    )
    @Transactional(rollbackFor = Exception.class)
    public AlbumInfoVO info(Long id) {
        Album album = albumMapper.selectById(id);
        Integer score = scoreService.select(id);
        Boolean collect = collectService.select(id);
        List<Song> songs = songService.selectByAlbumId(id);

        AlbumInfoVO albumInfoVO = new AlbumInfoVO();
        BeanUtils.copyProperties(album, albumInfoVO);
        albumInfoVO.setScore(score);
        albumInfoVO.setCollect(collect);
        albumInfoVO.setSongs(songs);
        return albumInfoVO;
    }

    /**
     * 处理平均分
     */
    @CacheEvict(
            value = {"albumPage", "albumInfo"},  // 同时需要清除的缓存名称
            allEntries = true  // 清除对应缓存中的所有数据
    )
    @Transactional(rollbackFor = Exception.class)
    public void processAvgScore() {
        List<ScoreCountDTO> scoreCountDTOS = scoreService.selectScoreCount(ResourceType.ALBUM);

        Map<Long, ScoreStats> scoreStatsMap = new HashMap<>();
        scoreCountDTOS.forEach(scoreCountDTO -> {
            Long resourceId = scoreCountDTO.getResourceId();
            Integer count = scoreCountDTO.getScoreCount();
            Integer score = scoreCountDTO.getScore();

            ScoreStats scoreStats = scoreStatsMap.computeIfAbsent(resourceId, k -> new ScoreStats());

            scoreStats.setScoreCount(scoreStats.getScoreCount() +count);
            scoreStats.setTotalScore(scoreStats.getTotalScore() + score * count);
        });
        List<Album> albums = new ArrayList<>();
        scoreStatsMap.forEach((key, scoreStats) -> albums.add(Album.builder()
                .id(key)
                .scoreCount(scoreStats.getScoreCount())
                .avgScore(scoreStats.getAvgScore())
                .build()
        ));
        albumMapper.updateById(albums);
    }

}
