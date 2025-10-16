package com.xxxyjade.hiphopghetto.common.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@TableName("activity")
public class Activity implements Serializable {

    /**
     * 活动Id
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
     * 描述
     */
    private String description;

    /**
     * 状态
     * 0 - 正常
     * 1 - 失效
     */
    @TableLogic(value = "0", delval = "1")
    private Integer status = 0;


    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

}
