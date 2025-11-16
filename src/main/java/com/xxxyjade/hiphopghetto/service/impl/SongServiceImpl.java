package com.xxxyjade.hiphopghetto.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxxyjade.hiphopghetto.common.constant.ResourceType;
import com.xxxyjade.hiphopghetto.common.enums.SortType;
import com.xxxyjade.hiphopghetto.common.pojo.dto.PageQueryDTO;
import com.xxxyjade.hiphopghetto.common.pojo.entity.*;
import com.xxxyjade.hiphopghetto.common.pojo.vo.PageVO;
import com.xxxyjade.hiphopghetto.common.pojo.vo.SongInfoVO;
import com.xxxyjade.hiphopghetto.mapper.*;
import com.xxxyjade.hiphopghetto.service.CollectService;
import com.xxxyjade.hiphopghetto.service.RatingService;
import com.xxxyjade.hiphopghetto.service.SongService;
import com.xxxyjade.hiphopghetto.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
public class SongServiceImpl implements SongService {

    @Autowired
    @Lazy
    private RatingService ratingService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private SongMapper songMapper;
    @Autowired
    private CollectService collectService;

    /**
     * （条件）分页查询
     */
    @Cacheable(
            value = "songPage",
            key = "'songPage::page=' + #pageQueryDTO.page + '&size=' + #pageQueryDTO.size + '&sort=' + #pageQueryDTO.sortType",
            unless = "#result == null"
    )
    @Transactional(rollbackFor = Exception.class)
    public PageVO<Song> page(PageQueryDTO pageQueryDTO) {
        SortType sortType = pageQueryDTO.getSortType();
        Page<Song> page = new Page<>(pageQueryDTO.getPage(), pageQueryDTO.getSize());
        if (sortType != SortType.DEFAULT) {
            page.setOrders(Collections.singletonList(OrderItem.desc(sortType.getType())));
        }
        songMapper.selectPage(page, null);
        return new PageVO<>(page.getTotal(), page.getRecords());
    }

    /**
     * 查询歌曲详情
     */
    @Cacheable(
            value ="songInfo",
            key = "'songInfo::id=' + #id",
            unless ="#result == null"
    )
    @Transactional(rollbackFor = Exception.class)
    public SongInfoVO info(Long id) {
        Song song = songMapper.selectById(id);
        Integer score = ratingService.select(id);
        Boolean collect = collectService.select(id);

        SongInfoVO albumInfoVO = new SongInfoVO();
        BeanUtils.copyProperties(song, albumInfoVO);
        albumInfoVO.setScore(score);
        albumInfoVO.setCollect(collect);
        return albumInfoVO;
    }

    /**
     * 查询专辑中全部歌曲
     */
    @Cacheable(
            value ="albumSongs",
            key ="'albumSongs::id' + #albumId",
            unless ="#result == null"
    )
    @Transactional(rollbackFor = Exception.class)
    public List<Song> selectByAlbumId(Long albumId) {
        return songMapper.selectList(
                new QueryWrapper<Song>()
                        .eq("album_id", albumId)
        );
    }

    /**
     * 插入歌曲，若存在则忽略
     */
    public void insertIgnore(Song song) {
        // 尝试插入
        int insertIgnore = songMapper.insertIgnore(song);

        if (insertIgnore == 1) {
            // 插入成功清除缓存
            redisUtil.deleteByPrefix("songPage::");
        }
    }

    /**
     * 更新专辑信息
     */
    public void update(List<Song> songs) {
        songMapper.updateById(songs);
        redisUtil.deleteByPrefix("songPage::");
        redisUtil.deleteByPrefix("songInfo::");
    }

    /**
     * 增加累计评分
     */
    public void increaseRatingCount(Long id) {
        int increase = songMapper.increaseRatingCount(id);
        if (increase == 1) {
            redisUtil.delete("songInfo::id=" + id);
        }
    }

}
