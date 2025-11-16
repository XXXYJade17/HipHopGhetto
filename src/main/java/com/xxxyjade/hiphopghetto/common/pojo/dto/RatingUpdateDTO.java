package com.xxxyjade.hiphopghetto.common.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "RatingUpdateDTO", description = "更新评分DTO")
public class RatingUpdateDTO {

    /**
     * 评分对象id
     */
    private Long targetId;

    /**
     * 评分
     */
    private Integer score;

}
