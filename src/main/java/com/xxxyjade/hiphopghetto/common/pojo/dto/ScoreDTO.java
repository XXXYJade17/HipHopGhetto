package com.xxxyjade.hiphopghetto.common.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "评分DTO")
public class ScoreDTO {

    @NotNull(message = "评分对象id不能为空")
    @Schema(description = "评分对象id")
    Long resourceId;

    @NotNull(message = "评分不能为空")
    @Schema(description = "评分")
    Integer score;

}
