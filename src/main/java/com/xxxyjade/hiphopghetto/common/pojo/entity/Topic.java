package com.xxxyjade.hiphopghetto.common.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.xxxyjade.hiphopghetto.common.pojo.base.BaseTableData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 话题实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("topic")
public class Topic extends BaseTableData {

    /**
     * 话题id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 封面url
     */
    private String coverUrl;

    /**
     * 评论区id
     */
    @TableField(fill = FieldFill.INSERT)
    private Long commentSectionId;

    /**
     * 浏览量
     */
    private Integer view;

    /**
     * 点赞量
     */
    private Integer like;

}
