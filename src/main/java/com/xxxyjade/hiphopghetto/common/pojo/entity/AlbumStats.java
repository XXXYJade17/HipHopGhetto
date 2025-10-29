package com.xxxyjade.hiphopghetto.common.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("album_stats")
public class AlbumStats extends BaseStats {

    /**
     * 专辑 Id
     */
    @TableId
    private Long albumId;

}
