package com.xxxyjade.hiphopghetto.common.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(title = "RatingCreateDTO", description = "创建评分DTO")
public class RatingCreateDTO {

    @NotNull
    @Schema(description = "评分对象id")
    private Long targetId;

    @NotNull
    @Schema(description = "评分对象类型")
    private Integer targetType;

    @NotNull
    @Schema(description = "评分")
    private Integer score;

}
