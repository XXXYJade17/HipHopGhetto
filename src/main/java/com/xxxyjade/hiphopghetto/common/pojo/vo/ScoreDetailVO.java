package com.xxxyjade.hiphopghetto.common.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "评分详情VO")
public class ScoreDetailVO {

    @Schema(name = "one", description = "一分数量")
    private Integer one;

    @Schema(name = "two", description = "二分数量")
    private Integer two;

    @Schema(name = "three", description = "三分数量")
    private Integer three;

    @Schema(name = "four", description = "四分数量")
    private Integer four;

    @Schema(name = "five", description = "五分数量")
    private Integer five;

    @Schema(name = "six", description = "六分数量")
    private Integer six;

    @Schema(name = "seven", description = "七分数量")
    private Integer seven;

    @Schema(name = "eight", description = "八分数量")
    private Integer eight;

    @Schema(name = "nine", description = "九分数量")
    private Integer nine;

    @Schema(name = "ten", description = "十分数量")
    private Integer ten;

}
