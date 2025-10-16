package com.xxxyjade.hiphopghetto.common.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtProperties {

    /**
     * 密钥
     */
    private String secretKey;

    /**
     * 令牌生命
     */
    private long ttl;

    /**
     * 令牌名
     */
    private String tokenName;
}
