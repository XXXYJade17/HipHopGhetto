package com.xxxyjade.hiphopghetto.common.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "打分分页查询DTO")
public class VotingPageDTO {

    @Schema(name = "page", description = "页码")
    private int page;

    @Schema(name = "pageSize", description = "每页大小")
    private int pageSize;

}
