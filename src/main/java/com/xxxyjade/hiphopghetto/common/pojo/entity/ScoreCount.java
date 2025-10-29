package com.xxxyjade.hiphopghetto.common.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScoreCount {

    /**
     * 专辑/歌曲 Id
     */
    private Long id;

    /**
     * 分数
     */
    private String score;

    /**
     * 记录数
     */
    private Integer count;

}
