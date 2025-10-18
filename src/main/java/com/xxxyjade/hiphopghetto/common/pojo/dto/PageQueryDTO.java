package com.xxxyjade.hiphopghetto.common.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(title = "分页查询DTO")
public class PageQueryDTO {

    @NotNull
    @Schema(description = "页数")
    private Integer page;

    @NotNull
    @Schema(description = "每页大小")
    private Integer pageSize;

}
