package com.xxxyjade.hiphopghetto.common.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("song")
public class Song {

    /**
     * 歌曲 Id
     */
    @TableId
    Long songId;

    /**
     * 歌曲名
     */
    String songName;

    /**
     * 所属专辑 Id
     */
    Long albumId;


    /**
     * 所属专辑 名
     */
    String albumName;

    /**
     * 歌手名
     */
    String singer;

    /**
     * 时长（秒）
     */
    Integer duration;

    /**
     * 封面 URL
     */
    String coverUrl;

    /**
     * 专辑综合评分
     */
    @TableField(exist = false)
    BigDecimal avgScore;

}
