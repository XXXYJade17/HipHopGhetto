package com.xxxyjade.hiphopghetto.common.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.xxxyjade.hiphopghetto.common.pojo.base.BaseTableData;
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
@TableName("user")
public class User extends BaseTableData implements Serializable {

    /**
     * id
     * 雪花生成
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

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
     * 简介
     */
    private String description;

    /**
     * 生日
     */
    private LocalDateTime birthday;


}
