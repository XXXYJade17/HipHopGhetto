package com.xxxyjade.hiphopghetto.common.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("song_score")
public class SongScore {

    /**
     * 主键
     */
    @TableId
    private Integer id;

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
    String score;

}

