package com.xxxyjade.hiphopghetto.common.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
@Schema(title = "帖子创建DTO")
public class PostingCreateDTO {

    @NotBlank
    @Schema(name = "title", description = "标题")
    private String title;

    @Schema(name = "content", description = "内容")
    private String content;

    @Schema(name = "image", description = "图片")
    private List<String> images;

}
