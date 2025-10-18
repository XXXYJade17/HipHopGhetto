package com.xxxyjade.hiphopghetto.common.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(title = "用户注册与登录 VO")
public class UserRegisterLoginVO {

    @Schema(description = "Jwt令牌")
    private String token;

}
