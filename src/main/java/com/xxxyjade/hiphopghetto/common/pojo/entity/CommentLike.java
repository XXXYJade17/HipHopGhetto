package com.xxxyjade.hiphopghetto.common.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("comment_like")
public class CommentLike {

    /**
     * 评论点赞记录id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 评论id
     */
    private Long commentId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 是否点赞
     */
    private Boolean like;

}
