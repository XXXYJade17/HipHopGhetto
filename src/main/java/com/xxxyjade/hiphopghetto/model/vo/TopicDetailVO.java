package com.xxxyjade.hiphopghetto.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "TopicDetailVO", description = "话题详情VO")
public class TopicDetailVO {

    @Schema(name = "content", description = "话题内容")
    private String content;

    @Schema(name = "viewCount", description = "浏览数")
    private Integer viewCount;

    @Schema(name = "commentCount", description = "评论数")
    private Integer commentCount;

    @Schema(name = "likeCount", description = "点赞数")
    private Integer likeCount;

    @Schema(name = "isLiked", description = "是否点赞")
    private Boolean isLiked;

    @Schema(name = "createTime", description = "创建时间")
    private LocalDateTime createTime;

}
