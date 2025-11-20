package com.xxxyjade.hiphopghetto.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "RatingDTO", description = "评分DTO")
public class RatingDTO {

    @Schema(name = "targetId", description = "评分对象id")
    private Long targetId;

    @Schema(name = "targetType", description = "评分对象类型")
    private Integer targetType;

    @Schema(name = "score", description = "评分分数，1-10分")
    private Integer score;

}
