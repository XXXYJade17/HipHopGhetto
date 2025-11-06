package com.xxxyjade.hiphopghetto.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxxyjade.hiphopghetto.common.enums.SortType;
import com.xxxyjade.hiphopghetto.common.pojo.dto.ScoreDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.PageQueryDTO;
import com.xxxyjade.hiphopghetto.common.pojo.entity.*;
import com.xxxyjade.hiphopghetto.common.pojo.vo.*;
import com.xxxyjade.hiphopghetto.mapper.*;
import com.xxxyjade.hiphopghetto.service.AlbumService;
import com.xxxyjade.hiphopghetto.util.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    private AlbumMapper albumMapper;
    @Autowired
    private CollectMapper collectMapper;
    @Autowired
    private ScoreMapper scoreMapper;
    @Autowired
    private SongMapper songMapper;

    /**
     * （条件）分页查询专辑
     */
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
    @Transactional(rollbackFor = Exception.class)
    public AlbumInfoVO info(Long id) {
        Long userId = ThreadUtil.getId();
        Album album = albumMapper.selectById(id);
        Integer score = scoreMapper.isScored(userId, id);
        Boolean collect = collectMapper.isCollected(userId, id);
        List<Song> songs = songMapper.selectList(new QueryWrapper<Song>().eq("album_id", id));

        AlbumInfoVO albumInfoVO = new AlbumInfoVO();
        BeanUtils.copyProperties(album, albumInfoVO);
        albumInfoVO.setScore(score);
        albumInfoVO.setCollect(collect);
        albumInfoVO.setSongs(songs);
        return albumInfoVO;
    }

    /**
     * 创建/修改专辑评分
     */
    public void score(ScoreDTO scoreDTO) {
        Long userId = ThreadUtil.getId();
        Score score = Score.builder()
                .userId(userId)
                .resourceId(scoreDTO.getResourceId())
                .score(scoreDTO.getScore())
                .build();
        scoreMapper.insertOrUpdateScore(score);
    }

    /**
     * 收藏/取消收藏专辑
     */
    public void collect(Long id) {
        Long userId = ThreadUtil.getId();
        collectMapper.collect(userId, id);
    }

}
