package com.xxxyjade.hiphopghetto.common.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@TableName("score_image")
public class ScoreImage implements Serializable {

    /**
     * 图片ID
     * 自增长
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 评分ID
     */
    private Long scoreId;

    /**
     * 图片URL
     */
    private String url;


}
