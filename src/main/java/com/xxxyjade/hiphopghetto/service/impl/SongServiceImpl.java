package com.xxxyjade.hiphopghetto.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxxyjade.hiphopghetto.common.pojo.dto.PageQueryDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.SongScoreDTO;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Song;
import com.xxxyjade.hiphopghetto.common.pojo.entity.SongScore;
import com.xxxyjade.hiphopghetto.common.pojo.entity.SongScoreSummary;
import com.xxxyjade.hiphopghetto.common.pojo.vo.PageVO;
import com.xxxyjade.hiphopghetto.common.pojo.vo.SongScoreVO;
import com.xxxyjade.hiphopghetto.common.pojo.vo.SongVO;
import com.xxxyjade.hiphopghetto.mapper.SongMapper;
import com.xxxyjade.hiphopghetto.mapper.SongScoreMapper;
import com.xxxyjade.hiphopghetto.mapper.SongScoreSummaryMapper;
import com.xxxyjade.hiphopghetto.service.SongService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SongServiceImpl implements SongService {

    @Autowired
    private SongMapper songMapper;
    @Autowired
    private SongScoreMapper songScoreMapper;
    @Autowired
    private SongScoreSummaryMapper songScoreSummaryMapper;

    /**
     * 查询
     * @param pageQueryDTO 分页查询DTO
     * @return 分页VO
     */
    public PageVO<Song> page(PageQueryDTO pageQueryDTO) {
        Page<Song> page = songMapper.selectPage(new Page<>(pageQueryDTO.getPage(), pageQueryDTO.getSize()), null);
        PageVO<Song> pageVO = new PageVO<>();
        pageVO.setList(page.getRecords());
        return pageVO;
    }

    /**
     * 查询详情
     * @param id 歌曲Id
     * @return 歌曲VO
     */
    public SongVO info(Long id) {
        Song song = songMapper.selectById(id);
        SongVO songVO = new SongVO();
        BeanUtils.copyProperties(song, songVO);
        return songVO;
    }

    /**
     * 歌曲评分
     * @param songScoreDTO 歌曲评分DTO
     */
    public void score(SongScoreDTO songScoreDTO) {
        SongScore songScore = new SongScore();
        BeanUtils.copyProperties(songScoreDTO, songScore);
        songScoreMapper.insert(songScore);
    }

    /**
     * 获取评分
     * @param id 歌曲id
     * @return 歌曲评分VO
     */
    public SongScoreVO getScore(Long id) {
        SongScoreSummary songScoreSummary = songScoreSummaryMapper.selectById(id);
        SongScoreVO songScoreVO = new SongScoreVO();
        BeanUtils.copyProperties(songScoreSummary, songScoreVO);
        return songScoreVO;
    }

}
