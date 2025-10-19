package com.xxxyjade.hiphopghetto.common.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "用户注册 VO")
public class UserRegisterVO {

    @Schema(description = "用户Id")
    private Long id;

    @Schema(description = "Jwt令牌")
    private String token;

}
