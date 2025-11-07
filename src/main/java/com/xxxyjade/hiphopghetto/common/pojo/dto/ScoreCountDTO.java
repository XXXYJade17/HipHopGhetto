package com.xxxyjade.hiphopghetto.common.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScoreCountDTO {

    private Long resourceId;

    private Integer score;

    private Integer scoreCount;

}
