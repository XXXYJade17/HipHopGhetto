package com.xxxyjade.hiphopghetto.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 评分实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("rating")
public class Rating {

    /**
     * 评分id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 评分对象id
     */
    private Long targetId;

    /**
     * 评分对象类型
     * 1 - 专辑
     * 2 - 歌曲
     */
    private Integer targetType;

    /**
     * 评分
     */
    private Integer score;

}
