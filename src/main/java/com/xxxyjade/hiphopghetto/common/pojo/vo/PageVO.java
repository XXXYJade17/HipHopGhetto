package com.xxxyjade.hiphopghetto.common.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "分页查询VO")
public class PageVO<T> {

    @Schema(description = "总记录数")
    private Long total;

    @Schema(description = "数据")
    private List<T> data;

}
