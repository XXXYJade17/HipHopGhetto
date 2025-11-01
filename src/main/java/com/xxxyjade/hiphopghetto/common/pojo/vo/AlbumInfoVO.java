package com.xxxyjade.hiphopghetto.common.pojo.vo;

import com.xxxyjade.hiphopghetto.common.pojo.entity.AlbumStats;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Song;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "专辑详情VO")
public class AlbumInfoVO {

    @Schema(description = "专辑名")
    private String albumName;

    @Schema(description = "歌手名")
    private String singer;

    @Schema(description = "发行时间")
    private LocalDate releaseTime;

    @Schema(description = "封面url")
    private String coverUrl;

    @Schema(description = "专辑简介")
    private String description;

    @Schema(description = "用户评分")
    private Integer score;

    @Schema(description = "收藏状态 false-未收藏 true-已收藏")
    private Boolean collect;

//    @Schema(description = "专辑统计数据")
//    private AlbumStats albumStats;

    @Schema(description = "平均评分")
    private BigDecimal avgScore;

    @Schema(description = "评分数")
    private Integer scoreCount;

    @Schema(description = "收藏数")
    private Integer collectCount;

    @Schema(description = "评论数")
    private Integer commentCount;

    @Schema(description = "专辑歌曲")
    private List<Song> songs;

}
