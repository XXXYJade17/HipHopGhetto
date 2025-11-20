package com.xxxyjade.hiphopghetto.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 收藏实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("collection")
public class Collection {

    /**
     * 评分记录id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 收藏对象id
     */
    private Long targetId;

    /**
     * 收藏对象类型
     * 1 - 专辑
     * 2 - 歌曲
     */
    private Integer targetType;

    /**
     * 是否收藏
     */
    private Boolean isCollected;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

}
