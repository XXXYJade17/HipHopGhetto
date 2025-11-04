package com.xxxyjade.hiphopghetto.common.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "评论分页查询DTO")
public class CommentPageQueryDTO extends PageQueryDTO {

    @Schema(description = "评论区id")
    private Long commentSectionId;

}
