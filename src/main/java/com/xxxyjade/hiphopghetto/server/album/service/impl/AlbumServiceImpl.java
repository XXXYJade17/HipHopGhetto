package com.xxxyjade.hiphopghetto.server.album.service.impl;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxxyjade.hiphopghetto.model.enums.SortType;
import com.xxxyjade.hiphopghetto.model.dto.PageQueryDTO;
import com.xxxyjade.hiphopghetto.model.entity.Album;
import com.xxxyjade.hiphopghetto.model.entity.Song;
import com.xxxyjade.hiphopghetto.model.vo.AlbumDetailVO;
import com.xxxyjade.hiphopghetto.model.vo.PageVO;
import com.xxxyjade.hiphopghetto.server.album.mapper.AlbumMapper;
import com.xxxyjade.hiphopghetto.server.album.service.AlbumService;
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
public class AlbumServiceImpl implements AlbumService {

    private final AlbumMapper albumMapper;
    private final RedisUtil redisUtil;
    private final SongService songService;

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
            value ="albumDetail",
            key = "'albumDetail::id=' + #id",
            unless ="#result == null"
    )
    @Transactional(rollbackFor = Exception.class)
    public AlbumDetailVO detail(Long id) {
        Album album = albumMapper.selectById(id);
        List<Song> songs = songService.selectByAlbumId(id);

        AlbumDetailVO albumDetailVO = new AlbumDetailVO();
        BeanUtils.copyProperties(album, albumDetailVO);
        albumDetailVO.setSongs(songs);
        return albumDetailVO;
    }

    /**
     * 插入专辑，若存在则忽略
     */
    @Transactional(rollbackFor = Exception.class)
    public void insertIgnore(Album album) {
        // 尝试插入
        int insertIgnore = albumMapper.insertIgnore(album);

        if (insertIgnore == 1) {
            // 插入成功清除缓存
            redisUtil.deleteByPrefix("albumPage::");
        }
    }

    /**
     * 收藏数递增
     */
    @CacheEvict(
            value = "albumDetail",
            key = "'albumDetail::id=' + #id"
    )
    @Transactional(rollbackFor = Exception.class)
    public void increaseCollectionCount(Long id) {
        albumMapper.increaseCollectionCount(id);
    }

    /**
     * 收藏数递减
     */
    @CacheEvict(
            value = "albumDetail",
            key = "'albumDetail::id=' + #id"
    )
    @Transactional(rollbackFor = Exception.class)
    public void decreaseCollectionCount(Long id) {
        albumMapper.decreaseCollectionCount(id);
    }

    /**
     * 评分数递增
     */
    @CacheEvict(
            value = "albumDetail",
            key = "'albumDetail::id=' + #id"
    )
    @Transactional(rollbackFor = Exception.class)
    public void increaseRatingCount(Long id) {
        albumMapper.increaseRatingCount(id);
    }

}
