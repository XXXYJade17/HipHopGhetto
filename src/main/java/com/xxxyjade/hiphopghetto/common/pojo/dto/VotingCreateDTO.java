package com.xxxyjade.hiphopghetto.common.pojo.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Schema(title = "打分创建 DTO")
public class VotingCreateDTO implements Serializable {

    @Schema(name = "userId", description = "发起用户Id")
    private Long userId;

    @Schema(name = "title", description = "标题")
    private String title;

    @Schema(name = "description", description = "描述")
    private String description;

    @Schema(name = "images", description = "附录图片")
    private List<String> images;

}
