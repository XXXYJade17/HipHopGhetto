package com.xxxyjade.hiphopghetto.common.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(title = "用户登录 DTO")
public class UserLoginDTO implements Serializable {

    @NotBlank(message = "账号不能为空")
    @Schema(description = "用户名/手机号/邮箱")
    private String account;

    @NotBlank(message = "密码不能为空")
    @Size(min = 8, max = 32, message = "密码长度必须在8-32个字符之间")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$", message = "密码必须包含字母和数字")
    @Schema(description = "密码")
    private String password;

}
