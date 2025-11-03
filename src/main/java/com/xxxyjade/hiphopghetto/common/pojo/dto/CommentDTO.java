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
@Schema(title = "评论DTO")
public class CommentDTO {

    @Schema(description = "评论区id")
    private Long commentSectionId;

    @Schema(description = "回复对象id")
    private Long replyId;

    @Schema(description = "评论内容")
    private String content;

}
