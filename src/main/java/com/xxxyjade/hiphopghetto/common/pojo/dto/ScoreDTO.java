package com.xxxyjade.hiphopghetto.common.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "评分DTO")
public class ScoreDTO {

    @NotNull
    @Schema(description = "评分对象id")
    private Long resourceId;

    @NotNull
    @Schema(description = "评分对象类型")
    private Integer resourceType;

    @NotNull
    @Schema(description = "评分")
    private Integer score;

}
