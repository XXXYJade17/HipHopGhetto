package com.xxxyjade.hiphopghetto.common.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@TableName("song_score_summary")
public class SongScoreSummary {

    private Long id;

    private Integer total;

    private BigDecimal score;

    /**
     * 评分情况
     */
    Integer one;
    Integer two;
    Integer three;
    Integer four;
    Integer five;
    Integer six;
    Integer seven;
    Integer eight;
    Integer nine;
    Integer ten;

}
