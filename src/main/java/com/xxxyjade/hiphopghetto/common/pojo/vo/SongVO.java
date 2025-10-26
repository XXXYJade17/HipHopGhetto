package com.xxxyjade.hiphopghetto.common.pojo.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "歌曲详情VO")
public class SongVO {

    @Schema(description = "歌曲名")
    String name;

    @Schema(description = "所属专辑Id")
    Long albumId;

    @Schema(description = "所属专辑名")
    String albumName;

    @Schema(description = "歌手名")
    String singer;

    @Schema(description = "时长（秒）")
    Integer duration;

    @Schema(description = "封面URL")
    String url;

}
