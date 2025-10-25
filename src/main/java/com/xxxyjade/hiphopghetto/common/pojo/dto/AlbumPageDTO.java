package com.xxxyjade.hiphopghetto.common.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AlbumPageDTO {

    @Schema(description = "页码")
    private Integer page;

    @Schema(description = "记录数")
    private Integer size;

}
