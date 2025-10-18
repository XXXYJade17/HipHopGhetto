package com.xxxyjade.hiphopghetto.common.pojo.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Schema(title = "评分创建DTO")
public class ScoreCreateDTO implements Serializable {

    @NotNull
    @Schema(name = "userId", description = "发起用户Id")
    private Long userId;

    @NotBlank(message = "标题不能为空")
    @Size(max = 20, message = "标题长度不能超过20")
    @Schema(name = "title", description = "标题")
    private String title;

    @Schema(name = "description", description = "描述")
    private String description;

    @Schema(name = "images", description = "附录图片")
    private List<String> images;

}
