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
@TableName("like")
public class Like {

    /**
     * 点赞记录id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 点赞对象id
     */
    private Long resourceId;

    /**
     * 用户id
     */
    private Long userId;

}
