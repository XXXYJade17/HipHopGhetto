package com.xxxyjade.hiphopghetto.common.pojo.vo;

import com.xxxyjade.hiphopghetto.common.pojo.entity.AlbumStats;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Song;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "专辑详情VO")
public class AlbumInfoVO {

    @Schema(description = "专辑统计数据")
    AlbumStats albumStats;

    @Schema(description = "专辑歌曲")
    List<Song> songs;

}
