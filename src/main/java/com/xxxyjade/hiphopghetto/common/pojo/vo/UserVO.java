package com.xxxyjade.hiphopghetto.common.pojo.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "用户信息 VO")
public class UserVO {

    /**
     * 用户名
     */
    private String username;

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
     * 生日
     */
    private LocalDateTime birthday;

}
