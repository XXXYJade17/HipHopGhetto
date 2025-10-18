package com.xxxyjade.hiphopghetto.common.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.xxxyjade.hiphopghetto.common.pojo.base.BaseTableData;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@TableName("posting_comment")
public class PostingComment extends BaseTableData implements Serializable {

    /**
     * 评论Id
     * 雪花生成
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 帖子Id
     */
    private Long postingId;

    /**
     * 评论用户Id
     */
    private Long userId;

    /**
     * 内容
     */
    private String content;

}
