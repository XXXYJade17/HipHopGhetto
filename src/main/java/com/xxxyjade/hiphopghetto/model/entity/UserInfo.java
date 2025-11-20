package com.xxxyjade.hiphopghetto.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("user_info")
public class UserInfo implements Serializable {

    /**
     * 用户id
     */
    private Long id;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 性别
     * 0 - 未知
     * 1 - 男
     * 2 - 女
     */
    private Integer sex;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 背景图
     */
    private String background;

    /**
     * 简介
     */
    private String description;

    /**
     * 生日
     */
    private LocalDateTime birthday;

}
