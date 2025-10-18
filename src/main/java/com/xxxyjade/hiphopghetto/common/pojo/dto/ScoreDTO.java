package com.xxxyjade.hiphopghetto.common.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(title = "评分DTO")
public class ScoreDTO implements Serializable {

    @Schema(name = "activityId", description = "活动ID")
    private Long activityId;

    @Schema(name = "score", description = "得分1-10")
    private Integer score;

}
