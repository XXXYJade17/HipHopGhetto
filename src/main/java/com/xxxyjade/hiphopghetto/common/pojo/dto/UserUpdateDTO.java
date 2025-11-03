package com.xxxyjade.hiphopghetto.common.pojo.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(title = "用户更新DTO")
public class UserUpdateDTO {

    /**
     * 用户名
     */
//    private String username;

    /**
     * 密码
     */
//    private String password;

    /**
     * 姓名
     */
//    private String name;

    /**
     * 身份证号
     */
//    private String idCard;

    /**
     * 手机号
     */
//    private String phone;

    /**
     * 邮箱
     */
//    private String email;

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
