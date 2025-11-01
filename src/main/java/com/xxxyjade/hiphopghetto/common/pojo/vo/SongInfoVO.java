package com.xxxyjade.hiphopghetto.common.pojo.vo;


import com.xxxyjade.hiphopghetto.common.pojo.entity.SongStats;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "歌曲详情VO")
public class SongInfoVO {

    @Schema(description = "歌曲名")
    private String songName;

    @Schema(description = "所属专辑Id")
    private Long albumId;

    @Schema(description = "所属专辑名")
    private String albumName;

    @Schema(description = "歌手名")
    private String singer;

    @Schema(description = "时长（秒）")
    private Integer duration;

    @Schema(description = "封面URL")
    private String coverUrl;

//    @Schema(description = "歌曲统计数据")
//    private SongStats songStats;

    @Schema(description = "平均评分")
    private BigDecimal avgScore;

    @Schema(description = "评分数")
    private Integer scoreCount;

    @Schema(description = "收藏数")
    private Integer collectCount;

    @Schema(description = "评论数")
    private Integer commentCount;

}
