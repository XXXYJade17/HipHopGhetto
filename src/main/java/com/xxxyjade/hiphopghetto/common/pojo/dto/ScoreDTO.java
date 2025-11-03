package com.xxxyjade.hiphopghetto.common.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "专辑/歌曲评分DTO")
public class ScoreDTO {

    @NotNull(message = "专辑/歌曲id不能为空")
    @Schema(description = "专辑/歌曲id")
    Long resourceId;

    @NotNull(message = "评分不能为空")
    @Schema(description = "评分")
    Integer score;

}
