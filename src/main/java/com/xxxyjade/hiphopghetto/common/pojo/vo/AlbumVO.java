package com.xxxyjade.hiphopghetto.common.pojo.vo;

import com.xxxyjade.hiphopghetto.common.pojo.entity.Song;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@Schema(title = "专辑详情VO")
public class AlbumVO {

    @Schema(description = "专辑名")
    String name;

    @Schema(description = "歌手名")
    String singer;

    @Schema(description = "发行时间")
    LocalDate releaseTime;

    @Schema(description = "专辑封面")
    String url;

    @Schema(description = "专辑介绍")
    String introduction;

    @Schema(description = "专辑歌曲")
    List<Song> songs;

}
