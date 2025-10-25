package com.xxxyjade.hiphopghetto.common.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AlbumScoreDTO {

    @Schema(description = "用户Id")
    Long userId;

    @Schema(description = "专辑Id")
    Long albumId;

    @Schema(description = "评分")
    Integer score;

}
