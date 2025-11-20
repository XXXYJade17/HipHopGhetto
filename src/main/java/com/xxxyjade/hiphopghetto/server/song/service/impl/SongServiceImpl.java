package com.xxxyjade.hiphopghetto.server.song.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxxyjade.hiphopghetto.model.enums.SortType;
import com.xxxyjade.hiphopghetto.model.dto.PageQueryDTO;
import com.xxxyjade.hiphopghetto.model.vo.PageVO;
import com.xxxyjade.hiphopghetto.model.vo.SongDetailVO;
import com.xxxyjade.hiphopghetto.model.entity.Song;
import com.xxxyjade.hiphopghetto.server.song.mapper.SongMapper;
import com.xxxyjade.hiphopghetto.server.song.service.SongService;
import com.xxxyjade.hiphopghetto.util.RedisUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class SongServiceImpl implements SongService {

    private final RedisUtil redisUtil;
    private final SongMapper songMapper;

    /**
     * （条件）分页查询歌曲
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
            value ="songDetail",
            key = "'songDetail::id=' + #id",
            unless ="#result == null"
    )
    @Transactional(rollbackFor = Exception.class)
    public SongDetailVO detail(Long id) {
        Song song = songMapper.selectById(id);

        SongDetailVO songDetailVO = new SongDetailVO();
        BeanUtils.copyProperties(song, songDetailVO);
        return songDetailVO;
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
    @Transactional(rollbackFor = Exception.class)
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
    @Transactional(rollbackFor = Exception.class)
    public void update(List<Song> songs) {
        songMapper.updateById(songs);
        redisUtil.deleteByPrefix("songPage::");
        redisUtil.deleteByPrefix("songDetail::");
    }

    /**
     * 收藏数递增
     */
    @CacheEvict(
            value = "songDetail",
            key = "'songDetail::id=' + #id"
    )
    @Transactional(rollbackFor = Exception.class)
    public void increaseCollectionCount(Long id) {
        songMapper.increaseCollectionCount(id);
    }

    /**
     * 收藏数递减
     */
    @CacheEvict(
            value = "songDetail",
            key = "'songDetail::id=' + #id"
    )
    @Transactional(rollbackFor = Exception.class)
    public void decreaseCollectionCount(Long id) {
        songMapper.decreaseCollectionCount(id);
    }

    /**
     * 评分数递增
     */
    @CacheEvict(
            value = "songDetail",
            key = "'songDetail::id=' + #id"
    )
    @Transactional(rollbackFor = Exception.class)
    public void increaseRatingCount(Long id) {
        songMapper.increaseRatingCount(id);
    }

}
