package com.xxxyjade.hiphopghetto.common.pojo.dto;

import com.xxxyjade.hiphopghetto.common.enums.SortType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(title = "分页查询DTO")
public class PageQueryDTO {

    @Schema(description = "排序方式")
    private SortType sortType = SortType.DEFAULT;

    @NotNull(message = "页数不能为空")
    @Schema(description = "页数")
    private Integer page;

    @NotNull(message = "记录数不能为空")
    @Schema(description = "记录数")
    private Integer size;

}
