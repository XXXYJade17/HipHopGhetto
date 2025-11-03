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
    @TableField(exist = false)
    private Long totalScore;

    /**
     * 综合评分
     */
    private BigDecimal avgScore;

    /**
     * 评分总数
     */
    private Integer scoreCount;

    /**
     * 收藏总数
     */
    private Integer collectCount;

    /**
     * 评论总数
     */
    private Integer commentCount;

}
