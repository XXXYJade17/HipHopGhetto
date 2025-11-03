package com.xxxyjade.hiphopghetto.common.pojo.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "歌曲详情VO")
public class SongInfoVO {

    @Schema(description = "网易云id")
    private Long neteaseId;

    @Schema(description = "歌曲名")
    private String songName;

    @Schema(description = "所属专辑Id")
    private Long albumId;

    @Schema(description = "所属专辑名")
    private String albumName;

    @Schema(description = "歌手名")
    private String singer;

    @Schema(description = "发行时间")
    private LocalDate releaseTime;

    @Schema(description = "时长（秒）")
    private Integer duration;

    @Schema(description = "封面URL")
    private String coverUrl;

    @Schema(description = "评论区id")
    private Long commentSectionId;

    @Schema(description = "综合评分")
    private BigDecimal avgScore;

    @Schema(description = "评分总数")
    private Integer scoreCount;

    @Schema(description = "收藏总数")
    private Integer collectCount;

    @Schema(description = "评论总数")
    private Integer commentCount;

    @Schema(description = "用户评分")
    private Integer score;

    @Schema(description = "是否收藏")
    private Boolean collect;

}
