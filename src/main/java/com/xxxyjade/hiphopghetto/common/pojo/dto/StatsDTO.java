package com.xxxyjade.hiphopghetto.common.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class StatsDTO {

    @Schema(description = "专辑/歌曲id")
    private Long resourceId;

    @Schema(description = "分数")
    private Integer score;

    @Schema(description = "分数记录数")
    private Integer scoreCount = 0;

    @Schema(description = "收藏记录数")
    private Integer collectCount = 0;

    @Schema(description = "评论记录数")
    private Integer commentCount = 0;

}
