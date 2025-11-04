package com.xxxyjade.hiphopghetto.common.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 歌曲实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("song")
public class Song {

    /**
     * 歌曲id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 网易云id
     */
    private Long neteaseId;

    /**
     * 歌名
     */
    private String songName;

    /**
     * 所属专辑id
     */
    private Long albumId;


    /**
     * 所属专辑名
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
     * 时长（秒）
     */
    private Integer duration;

    /**
     * 封面 URL
     */
    private String coverUrl;

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
