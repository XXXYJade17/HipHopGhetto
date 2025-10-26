package com.xxxyjade.hiphopghetto.common.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AlbumHasScoredDTO {

    @NotNull
    @Schema(description = "用户id")
    private Long userId;

    @NotNull
    @Schema(description = "专辑id")
    private Long albumId;

}
