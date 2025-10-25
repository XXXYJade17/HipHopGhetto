package com.xxxyjade.hiphopghetto.common.pojo.entity;

import lombok.Builder;
import lombok.Data;

@Data
public class SongScoreCount {

    private Long songId;

    private Integer score;

    private Integer count;

}
