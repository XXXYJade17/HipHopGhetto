package com.xxxyjade.hiphopghetto.common.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("album_score")
public class AlbumScore {

    /**
     * 用户Id
     */
    @TableId(type = IdType.AUTO)
    Long userId;

    /**
     * 专辑Id
     */
    Long albumId;

    /**
     * 评分
     */
    Integer score;

}

