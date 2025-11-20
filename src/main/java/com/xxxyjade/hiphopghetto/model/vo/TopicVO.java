package com.xxxyjade.hiphopghetto.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "TopicVO", description = "话题VO")
public class TopicVO {

    @Schema(name = "id", description = "话题id")
    private Long id;

    @Schema(name = "userId", description = "用户id")
    private Long userId;

    @Schema(name = "title", description = "话题标题")
    private String title;

    @Schema(name = "coverUrl", description = "话题封面")
    private String coverUrl;

    @Schema(name = "isLiked", description = "是否点赞")
    private Boolean isLiked;

}
