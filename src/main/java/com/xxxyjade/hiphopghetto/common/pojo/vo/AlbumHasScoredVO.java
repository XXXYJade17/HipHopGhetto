package com.xxxyjade.hiphopghetto.common.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AlbumHasScoredVO {

    @Schema(description = "有无评分")
    private Boolean hasScored;

    @Schema(description = "分数")
    private Integer score;


}
