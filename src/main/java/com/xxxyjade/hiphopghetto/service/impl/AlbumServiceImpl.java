package com.xxxyjade.hiphopghetto.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxxyjade.hiphopghetto.common.pojo.dto.AlbumHasScoredDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.AlbumScoreDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.PageQueryDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.SongScoreDTO;
import com.xxxyjade.hiphopghetto.common.pojo.entity.*;
import com.xxxyjade.hiphopghetto.common.pojo.vo.AlbumScoreVO;
import com.xxxyjade.hiphopghetto.common.pojo.vo.AlbumVO;
import com.xxxyjade.hiphopghetto.common.pojo.vo.PageVO;
import com.xxxyjade.hiphopghetto.common.pojo.vo.SongScoreVO;
import com.xxxyjade.hiphopghetto.mapper.AlbumMapper;
import com.xxxyjade.hiphopghetto.mapper.AlbumScoreMapper;
import com.xxxyjade.hiphopghetto.mapper.AlbumScoreSummaryMapper;
import com.xxxyjade.hiphopghetto.mapper.SongMapper;
import com.xxxyjade.hiphopghetto.service.AlbumService;
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
    private SongMapper songMapper;
    @Autowired
    private AlbumScoreMapper albumScoreMapper;
    @Autowired
    private AlbumScoreSummaryMapper albumScoreSummaryMapper;


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
     * 专辑评分
     * @param albumScoreDTO 专辑评分 DTO
     */
    public void score(AlbumScoreDTO albumScoreDTO) {
        AlbumScore albumScore = new AlbumScore();
        BeanUtils.copyProperties(albumScoreDTO, albumScore);
        albumScoreMapper.insertOrUpdate(albumScore);
    }

    /**
     * 获取评分
     * @param id 专辑 Id
     * @return 专辑评分 VO
     */
    public AlbumScoreVO getScore(Long id) {
        AlbumScoreSummary albumScoreSummary = albumScoreSummaryMapper.selectById(id);
        AlbumScoreVO albumScoreVO = new AlbumScoreVO();
        BeanUtils.copyProperties(albumScoreSummary, albumScoreVO);
        return albumScoreVO;
    }

    public Integer hasScore(AlbumHasScoredDTO albumHasScoredDTO) {
        return albumScoreMapper.select(albumHasScoredDTO.getUserId(), albumHasScoredDTO.getAlbumId());
    }

}
