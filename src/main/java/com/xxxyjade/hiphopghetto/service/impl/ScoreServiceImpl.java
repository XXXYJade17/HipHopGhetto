package com.xxxyjade.hiphopghetto.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxyjade.hiphopghetto.common.constant.Number;
import com.xxxyjade.hiphopghetto.common.pojo.dto.ScoreCreateDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.ScoreDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.PageQueryDTO;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Score;
import com.xxxyjade.hiphopghetto.common.pojo.entity.ScoreDetail;
import com.xxxyjade.hiphopghetto.common.pojo.entity.ScoreImage;
import com.xxxyjade.hiphopghetto.common.pojo.vo.ScoreDetailVO;
import com.xxxyjade.hiphopghetto.common.pojo.vo.PageVO;
import com.xxxyjade.hiphopghetto.mapper.ScoreDetailMapper;
import com.xxxyjade.hiphopghetto.mapper.ScoreImageMapper;
import com.xxxyjade.hiphopghetto.mapper.ScoreMapper;
import com.xxxyjade.hiphopghetto.service.ScoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class ScoreServiceImpl extends ServiceImpl<ScoreMapper, Score> implements ScoreService {

    @Autowired
    private ScoreMapper scoreMapper;
    @Autowired
    private ScoreImageMapper scoreImageMapper;
    @Autowired
    private ScoreDetailMapper scoreDetailMapper;

    /**
     * 创建评分
     *
     * @param scoreCreateDTO 活动创建DTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Void create(ScoreCreateDTO scoreCreateDTO) {
        // 插入评分
        Score score = Score.builder()
                .userId(scoreCreateDTO.getUserId())
                .title(scoreCreateDTO.getTitle())
                .description(scoreCreateDTO.getDescription())
                .build();
        scoreMapper.insert(score);

        List<String> images = scoreCreateDTO.getImages();

        // 判断是否存在图片
        if (images == null || images.isEmpty()) {
            return null;
        }

        // 批量插入图片
        images.forEach((String url)->{
            ScoreImage scoreImage = ScoreImage.builder()
                    .scoreId(score.getId())
                    .url(url)
                    .build();
            scoreImageMapper.insert(scoreImage);
        });

        // 创建评分详情
        ScoreDetail scoreDetail = ScoreDetail.builder()
                .scoreId(score.getId())
                .build();
        scoreDetailMapper.insert(scoreDetail);

        return null;
    }

    /**
     * 打分
     * @param scoreDTO 打分DTO
     */
    public Void vote(ScoreDTO scoreDTO) {
        Long activityId = scoreDTO.getActivityId();
        String num = Number.getStr(scoreDTO.getScore());
        scoreDetailMapper.vote(activityId, num);
        return null;
    }

    /**
     * 分页查询
     * @param pageQueryDTO 评分分页查询DTO
     * @return 评分分页查询VO
     */
    public PageVO<Score> page(PageQueryDTO pageQueryDTO) {
        Page<Score> page = new Page<>(pageQueryDTO.getPage(), pageQueryDTO.getPageSize());
        Page<Score> res = scoreMapper.selectPage(page, null);
        PageVO<Score> pageVO = new PageVO<>();
        pageVO.setList(res.getRecords());
        return pageVO;
    }

    /**
     * 评分详情
     * @param scoreId 评分ID
     * @return 评分详情VO
     */
    public ScoreDetailVO detail(Long scoreId) {
        ScoreDetail scoreDetail = scoreDetailMapper.selectById(scoreId);
        ScoreDetailVO scoreDetailVO = new ScoreDetailVO();
        BeanUtils.copyProperties(scoreDetail, scoreDetailVO);
        return scoreDetailVO;
    }

}
