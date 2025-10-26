package com.xxxyjade.hiphopghetto.common.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("album_score")
public class AlbumScore {

    /**
     * 主键自增
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 专辑Id
     */
    private Long albumId;

    /**
     * 评分
     */
    private Integer score;

}

