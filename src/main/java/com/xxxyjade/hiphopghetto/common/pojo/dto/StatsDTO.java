package com.xxxyjade.hiphopghetto.common.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatsDTO {

    @Schema(description = "专辑/歌曲id")
    private Long resourceId;

    @Schema(description = "分数")
    private Integer score = 0;

    @Builder.Default
    @Schema(description = "分数记录数")
    private Integer scoreCount = 0;

    @Builder.Default
    @Schema(description = "收藏记录数")
    private Integer collectCount = 0;

    @Builder.Default
    @Schema(description = "评论记录数")
    private Integer commentCount = 0;

}
