package com.xxxyjade.hiphopghetto.common.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@TableName("activity_image")
public class Image {

    /**
     * 活动ID
     */
    private Long activityId;

    /**
     * 图片URL
     */
    private String url;


}
