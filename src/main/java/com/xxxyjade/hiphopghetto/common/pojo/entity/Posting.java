package com.xxxyjade.hiphopghetto.common.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.xxxyjade.hiphopghetto.common.pojo.base.BaseTableData;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@TableName("posting")
public class Posting extends BaseTableData implements Serializable {

    /**
     * id
     * 雪花生成
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 发起用户Id
     */
    private Long userId;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 浏览量
     */
    private Integer view;

    /**
     * 点赞量
     */
    private Integer like;

}
