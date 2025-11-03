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
    @TableField(exist = false)
    private Long totalScore;

    /**
     * 综合评分
     */
    private BigDecimal avgScore;

    /**
     * 评分总数
     */
    private Integer scoreCount = 0;

    /**
     * 收藏总数
     */
    private Integer collectCount = 0;

    /**
     * 评论总数
     */
    private Integer commentCount = 0;

}
