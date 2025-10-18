package com.xxxyjade.hiphopghetto.common.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xxxyjade.hiphopghetto.common.pojo.base.BaseTableData;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@TableName("posting_like")
public class PostingLike extends BaseTableData {

    /**
     * 点赞记录Id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 帖子Id
     */
    private Long postingId;

    /**
     * 点赞用户Id
     */
    private Long userId;

}
