package com.xxxyjade.hiphopghetto.common.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Song {

    /**
     * 歌曲 Id
     */
    Long id;

    /**
     * 歌曲 名
     */
    String name;

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
    String url;

}
