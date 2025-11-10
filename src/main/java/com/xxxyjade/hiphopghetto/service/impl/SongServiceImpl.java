package com.xxxyjade.hiphopghetto.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxxyjade.hiphopghetto.common.enums.SortType;
import com.xxxyjade.hiphopghetto.common.pojo.dto.PageQueryDTO;
import com.xxxyjade.hiphopghetto.common.pojo.entity.*;
import com.xxxyjade.hiphopghetto.common.pojo.vo.PageVO;
import com.xxxyjade.hiphopghetto.common.pojo.vo.SongInfoVO;
import com.xxxyjade.hiphopghetto.mapper.*;
import com.xxxyjade.hiphopghetto.service.CollectService;
import com.xxxyjade.hiphopghetto.service.ScoreService;
import com.xxxyjade.hiphopghetto.service.SongService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class SongServiceImpl implements SongService {

    @Autowired
    private ScoreService scoreService;
    @Autowired
    private SongMapper songMapper;
    @Autowired
    private CollectService collectService;

    /**
     * （条件）分页查询
     */

    @Cacheable(
            value = "song",
            key = "'page=' + #pageQueryDTO.page + '&size=' + #pageQueryDTO.size + '&sort=' + #pageQueryDTO.sortType",
            unless = "#result == null"
    )
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
            value ="song  ",
            key ="#id",
            unless ="#result == null"
    )
    public SongInfoVO info(Long id) {
        Song song = songMapper.selectById(id);
        Integer score = scoreService.select(id);
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
    public List<Song> selectByAlbumId(Long albumId) {
        return songMapper.selectList(
                new QueryWrapper<Song>()
                        .eq("album_id", albumId)
        );
    }

}
