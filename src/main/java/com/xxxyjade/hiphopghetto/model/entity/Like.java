package com.xxxyjade.hiphopghetto.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("like_record")
public class Like {

    /**
     * 点赞记录id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 点赞目标id
     */
    private Long targetId;

    /**
     * 点赞目标类型
     */
    private Integer targetType;

    /**
     * 是否点赞
     */
    private Boolean isLiked;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

}
