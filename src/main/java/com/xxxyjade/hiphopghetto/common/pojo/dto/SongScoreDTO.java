package com.xxxyjade.hiphopghetto.common.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data

public class SongScoreDTO {

    @Schema(description = "歌曲Id")
    Long songId;

    @Schema(description = "评分")
    Integer score;

}
