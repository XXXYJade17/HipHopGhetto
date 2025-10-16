package com.xxxyjade.hiphopghetto.common.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@TableName("activity_score")
public class Score {

    /**
     * 活动ID
     */
    private Long activityId;

    /**
     * 1分
     */
    private Integer one;

    /**
     * 2分
     */
    private Integer two;

    /**
     * 3分
     */
    private Integer three;

    /**
     * 4分
     */
    private Integer four;

    /**
     * 5分
     */
    private Integer five;

    /**
     * 6分
     */
    private Integer six;

    /**
     * 7分
     */
    private Integer seven;

    /**
     * 8分
     */
    private Integer eight;

    /**
     * 9分
     */
    private Integer nine;

    /**
     * 10分
     */
    private Integer ten;

}
