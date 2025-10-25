package com.xxxyjade.hiphopghetto.common.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AlbumScoreVO {

    @Schema(description = "总记录数")
    private Integer total;

    @Schema(description = "综合评分")
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
