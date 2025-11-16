package com.xxxyjade.hiphopghetto.common.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 歌曲实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("song")
public class Song implements Serializable {

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
    private String artists;

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
     * 平均评分(百分制)
     */
    private Integer avgScore;

    /**
     * 累计评分
     */
    @Builder.Default
    private Integer ratingCount = 0;

    /**
     * 累计收藏
     */
    @Builder.Default
    private Integer collectCount = 0;

    /**
     * 累计评论
     */
    @Builder.Default
    private Integer commentCount = 0;

}
