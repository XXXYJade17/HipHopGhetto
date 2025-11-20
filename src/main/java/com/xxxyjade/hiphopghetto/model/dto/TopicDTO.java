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
@Schema(title = "TopicCreateDTO", description = "创建话题DTO")
public class TopicDTO {

    @Schema(title = "id", description = "话题id")
    private Long id;

    @NotBlank(message = "标题不能为空")
    @Schema(title = "title", description = "标题")
    private String title;

    @Schema(title = "content", description = "内容")
    private String content;

    @Schema(title = "coverUrl", description = "封面图片URL")
    private String coverUrl;

}
