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
@Schema(title = "CommentDTO", description = "评论创建DTO")
public class CommentDTO {

    @Schema(name = "parentId", description = "评论上级id")
    private Long parentId;

    @Schema(name = "parentType", description = "评论上级类型")
    private Integer parentType;

    @Schema(name = "content", description = "评论内容")
    private String content;

}
