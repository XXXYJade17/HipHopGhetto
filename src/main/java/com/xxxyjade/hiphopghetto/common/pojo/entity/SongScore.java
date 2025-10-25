package com.xxxyjade.hiphopghetto.common.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("song_score")
public class SongScore {

    /**
     * 用户Id
     */
    Long userId;

    /**
     * 歌曲Id
     */
    Long songId;

    /**
     * 评分
     */
    Integer score;

}

