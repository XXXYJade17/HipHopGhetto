package com.xxxyjade.hiphopghetto.common.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@TableName("posting_image")
public class PostingImage implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 帖子Id
     */
    private Long postingId;

    /**
     * 图片URL
     */
    private String url;

}
