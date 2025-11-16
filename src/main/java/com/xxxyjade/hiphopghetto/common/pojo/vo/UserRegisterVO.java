package com.xxxyjade.hiphopghetto.common.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Schema(title = "UserRegisterVO", description = "用户注册VO")
public class UserRegisterVO {

    @Schema(name = "id",
            description = "用户Id")
    private Long id;

    @Schema(name = "token",
            description = "用户id令牌")
    private String token;

}
