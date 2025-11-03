package com.xxxyjade.hiphopghetto.common.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xxxyjade.hiphopghetto.common.pojo.base.BaseTableData;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 评论实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("comment")
public class Comment extends BaseTableData {

    /**
     * 评论id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 评论区id
     */
    private Long commentSectionId;

    /**
     * 回复对象id
     */
    private Long replyId;

    /**
     * 评论内容
     */
    private String content;

}
