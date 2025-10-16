package com.xxxyjade.hiphopghetto.common.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(title = "用户注册 DTO")
public class UserRegisterDTO implements Serializable {

    @Schema(name = "username", description = "用户名")
    private String username;

    @Schema(name = "name", description = "姓名")
    private String name;

    @Schema(name = "password", description = "密码")
    private String password;

    @Schema(name = "phone", description = "手机号")
    private String phone;

    @Schema(name = "sex", description = "性别")
    private Integer sex;

    @Schema(name = "avatar", description = "头像")
    private String avatar;

}
