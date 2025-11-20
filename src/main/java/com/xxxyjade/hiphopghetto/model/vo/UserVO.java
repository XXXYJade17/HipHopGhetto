package com.xxxyjade.hiphopghetto.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "UserVO", description = "用户注册/登录VO")
public class UserVO {

    @Schema(name = "id", description = "用户Id")
    private Long id;

    @Schema(name = "token", description = "Jwt令牌")
    private String token;

}
