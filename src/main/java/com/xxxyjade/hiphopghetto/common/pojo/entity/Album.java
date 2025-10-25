package com.xxxyjade.hiphopghetto.common.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@TableName("album")
public class Album {

    /**
     * 专辑 Id
     */
    Long id;

    /**
     * 专辑名
     */
    String name;

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
    String url;

    /**
     * 专辑介绍
     */
    String introduction;

}
