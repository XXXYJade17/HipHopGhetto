package com.xxxyjade.hiphopghetto.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxxyjade.hiphopghetto.common.pojo.dto.PageQueryDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.ScoreDTO;
import com.xxxyjade.hiphopghetto.common.pojo.entity.*;
import com.xxxyjade.hiphopghetto.common.pojo.vo.PageVO;
import com.xxxyjade.hiphopghetto.common.pojo.vo.SongInfoVO;
import com.xxxyjade.hiphopghetto.mapper.*;
import com.xxxyjade.hiphopghetto.service.SongService;
import com.xxxyjade.hiphopghetto.util.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SongServiceImpl implements SongService {

    @Autowired
    private ScoreMapper scoreMapper;
    @Autowired
    private SongMapper songMapper;
    @Autowired
    private CollectMapper collectMapper;

    /**
     * （条件）分页查询
     */
    public PageVO<Song> page(PageQueryDTO pageQueryDTO) {
        QueryWrapper<Song> wrapper = new QueryWrapper<>();
        switch (pageQueryDTO.getSortType()){
            case AVG_SCORE -> wrapper.orderByDesc("avg_score");
            case COLLECT_COUNT -> wrapper.orderByDesc("collect_count");
            case COMMENT_COUNT -> wrapper.orderByDesc("comment_count");
            case NEAREST_TIME -> wrapper.orderByDesc("release_time");
        }
        Page<Song> page = songMapper
                .selectPage(new Page<>(pageQueryDTO.getPage(),
                        pageQueryDTO.getSize()), wrapper);
        PageVO<Song> pageVO = new PageVO<>();
        pageVO.setTotal(page.getTotal());
        pageVO.setData(page.getRecords());
        return pageVO;
    }

    /**
     * 查询歌曲详情
     */
    public SongInfoVO info(Long id) {
        Long userId = ThreadUtil.getId();
        Song song = songMapper.selectById(id);
        Integer score = scoreMapper.isScored(userId, id);
        Boolean collect = collectMapper.isCollected(userId, id);

        SongInfoVO albumInfoVO = new SongInfoVO();
        BeanUtils.copyProperties(song, albumInfoVO);
        albumInfoVO.setScore(score);
        albumInfoVO.setCollect(collect);
        return albumInfoVO;
    }

    /**
     * 创建/修改歌曲评分
     */
    public void score(ScoreDTO scoreDTO) {
        Long userId = ThreadUtil.getId();
        Score score = Score.builder()
                .userId(userId)
                .resourceId(scoreDTO.getResourceId())
                .score(scoreDTO.getScore())
                .build();
        scoreMapper.insertOrUpdate(score);
    }

    /**
     * 收藏/取消收藏专辑
     */
    public void collect(Long id) {
        Long userId = ThreadUtil.getId();
        collectMapper.collect(userId, id);
    }

}
