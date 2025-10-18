package com.xxxyjade.hiphopghetto.common.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(title = "分页查询VO")
public class PageVO<T> {

    @Schema(description = "数据集合")
    private List<T> list;

}
