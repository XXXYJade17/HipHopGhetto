package com.xxxyjade.hiphopghetto.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxxyjade.hiphopghetto.common.pojo.dto.AlbumScoreDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.PageQueryDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.SongScoreDTO;
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

import java.util.List;

@Service
@Slf4j
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    private AlbumMapper albumMapper;
    @Autowired
    private AlbumScoreMapper albumScoreMapper;
    @Autowired
    private AlbumScoreSummaryMapper albumScoreSummaryMapper;
    @Autowired
    private SongMapper songMapper;


    /**
     * 查询
     * @param pageQueryDTO 分页查询DTO
     * @return 分页VO
     */
    public PageVO<Album> page(PageQueryDTO pageQueryDTO) {
        Page<Album> page = albumMapper.selectPage(new Page<>(pageQueryDTO.getPage(), pageQueryDTO.getSize()), null);
        PageVO<Album> pageVO = new PageVO<>();
        pageVO.setList(page.getRecords());
        return pageVO;
    }

    /**
     * 查询详情
     * @param id 专辑Id
     * @return 专辑VO
     */
    @Transactional(rollbackFor = Exception.class)
    public AlbumVO info(Long id) {
        Album album = albumMapper.selectById(id);
        List<Song> songs = songMapper.selectList(new QueryWrapper<Song>().eq("album_id", album.getId()));
        return AlbumVO.builder()
                    .name(album.getName())
                    .singer(album.getSinger())
                    .releaseTime(album.getReleaseTime())
                    .url(album.getUrl())
                    .introduction(album.getIntroduction())
                    .songs(songs)
                    .build();
    }

    /**
     * 歌曲评分
     * @param albumScoreDTO 歌曲评分DTO
     */
    public void score(AlbumScoreDTO albumScoreDTO) {
        AlbumScore albumScore = new AlbumScore();
        BeanUtils.copyProperties(albumScoreDTO, albumScore);
        albumScoreMapper.insertOrUpdate(albumScore);
    }

    /**
     * 获取评分
     * @param id 歌曲id
     * @return 歌曲评分VO
     */
    @Transactional(rollbackFor = Exception.class)
    public AlbumScoreVO getScore(Long id) {
        if (!albumScoreSummaryMapper.exists(new QueryWrapper<AlbumScoreSummary>().eq("id", id))) {
            albumScoreSummaryMapper.insert(AlbumScoreSummary.builder().id(id).build());
        }
        AlbumScoreSummary albumScoreSummary = albumScoreSummaryMapper.selectById(id);
        AlbumScoreVO albumScoreVO = new AlbumScoreVO();
        BeanUtils.copyProperties(albumScoreSummary, albumScoreVO);
        return albumScoreVO;
    }

    /**
     * 插入专辑数据
     * @param album 专辑实体
     */
    @Transactional(rollbackFor = Exception.class)
    public void insert(Album album) {
        // 如果专辑数据不存在
        if (!albumMapper.exists(new QueryWrapper<Album>().eq("id", album.getId()))) {
            albumMapper.insert(album);
        }
        // 如果专辑评分汇总数据不存在
        if (!albumScoreSummaryMapper.exists(new QueryWrapper<AlbumScoreSummary>().eq("id", album.getId()))) {
            albumScoreSummaryMapper.insert(AlbumScoreSummary.builder().id(album.getId()).build());
        }
    }

    /**
     * 判断有无评分
     * @param id 专辑Id
     * @return 专辑有无评分VO
     */
    public AlbumHasScoredVO hasScored(Long id) {
        Long userId = ThreadUtil.getId();
        AlbumScore albumScore = albumScoreMapper.selectOne(new QueryWrapper<AlbumScore>().eq("user_id", userId).eq("album_id", id));
        AlbumHasScoredVO albumHasScoredVO = new AlbumHasScoredVO();
        albumHasScoredVO.setHasScored(albumScore != null);
        if (albumHasScoredVO.getHasScored()) {
            albumHasScoredVO.setScore(albumScore.getScore());
        }
        return albumHasScoredVO;
    }

}
