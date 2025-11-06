package com.xxxyjade.hiphopghetto.common.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 专辑实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("album")
public class Album {

    /**
     * 专辑id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 网易云id
     */
    private Long neteaseId;

    /**
     * 专辑名
     */
    private String albumName;

    /**
     * 歌手名
     */
    private String singer;

    /**
     * 发行时间
     */
    private LocalDate releaseTime;

    /**
     * 封面
     */
    private String coverUrl;

    /**
     * 简介
     */
    private String description;

    /**
     * 评论区id
     */
    @TableField(fill = FieldFill.INSERT)
    private Long commentSectionId;

    /**
     * 总评分
     */
    @Builder.Default
    @TableField(exist = false)
    private Long totalScore = 0L;

    /**
     * 综合评分
     */
    private BigDecimal avgScore;

    /**
     * 评分总数
     */
    @Builder.Default
    private Integer scoreCount = 0;

    /**
     * 收藏总数
     */
    @Builder.Default
    private Integer collectCount = 0;

    /**
     * 评论总数
     */
    @Builder.Default
    private Integer commentCount = 0;

    public void addTotalScore(int n) {
        totalScore += n;
    }

    public void addScoreCount(int n) {
        scoreCount += n;
    }

    public void addCollectCount(int n) {
        collectCount += n;
    }

    public void addCommentCount(int n) {
        commentCount += n;
    }

}
