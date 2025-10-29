package com.xxxyjade.hiphopghetto.common.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("album")
public class Album {

    /**
     * 专辑 Id
     */
    @TableId
    Long albumId;

    /**
     * 专辑名
     */
    String albumName;

    /**
     * 歌手名
     */
    String singer;

    /**
     * 发行时间
     */
    LocalDate releaseTime;

    /**
     * 专辑封面
     */
    String coverUrl;

    /**
     * 专辑简介
     */
    String description;

    /**
     * 专辑综合评分
     */
    @TableField(exist = false)
    BigDecimal avgScore;

}
