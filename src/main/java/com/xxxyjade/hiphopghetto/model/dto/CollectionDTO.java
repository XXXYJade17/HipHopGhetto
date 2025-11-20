package com.xxxyjade.hiphopghetto.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "CollectionDTO", description = "收藏DTO")
public class CollectionDTO {

    @Schema(name = "targetId", description = "收藏对象id")
    private Long targetId;

    @Schema(name = "targetType", description = "收藏资源类型：1-专辑 2-歌曲")
    private Integer targetType;

}
