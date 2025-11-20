package com.xxxyjade.hiphopghetto.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "CommentRequestDTO", description = "评论请求DTO")
public class CommentRequestDTO {

    @NotBlank(message = "评论内容不能为空")
    @Schema(description = "评论内容")
    private String content;

}
