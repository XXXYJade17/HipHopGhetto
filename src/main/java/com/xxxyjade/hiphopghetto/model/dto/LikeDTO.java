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
@Schema(title = "LikeDTO", description = "点赞DTO")
public class LikeDTO {

    @Schema(name = "targetId", description = "收藏对象id")
    private Long targetId;

    @Schema(name = "targetType", description = "收藏资源类型：3-评论 4-话题")
    private Integer targetType;

}
