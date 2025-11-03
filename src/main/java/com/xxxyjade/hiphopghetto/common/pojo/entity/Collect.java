package com.xxxyjade.hiphopghetto.common.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 收藏实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("collect")
public class Collect {

    /**
     * 评分记录id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 专辑/歌曲id
     */
    private Long resourceId;

    /**
     * 收藏
     */
    private Boolean collect;

}
