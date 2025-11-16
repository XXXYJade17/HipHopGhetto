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
import com.xxxyjade.hiphopghetto.service.RatingService;
import com.xxxyjade.hiphopghetto.service.SongService;
import com.xxxyjade.hiphopghetto.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.BatchResult;
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
    private RedisUtil redisUtil;
    @Autowired
    @Lazy
    private RatingService ratingService;
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
        Integer score = ratingService.select(id);
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
     * 插入专辑，若存在则忽略
     */
    public void insertIgnore(Album album) {
        // 尝试插入
        int insertIgnore = albumMapper.insertIgnore(album);

        if (insertIgnore == 1) {
            // 插入成功清除缓存
            redisUtil.deleteByPrefix("albumPage::");
        }
    }

    /**
     * 更新专辑信息
     */
    public void update(List<Album> albums) {
        albumMapper.updateById(albums);
        redisUtil.deleteByPrefix("albumPage::");
        redisUtil.deleteByPrefix("albumInfo::");
    }

    /**
     * 增加累计评分
     */
    public void increaseRatingCount(Long id) {
        int increase = albumMapper.increaseRatingCount(id);
        if (increase == 1) {
            redisUtil.delete("albumInfo::id=" + id);
        }
    }

}
