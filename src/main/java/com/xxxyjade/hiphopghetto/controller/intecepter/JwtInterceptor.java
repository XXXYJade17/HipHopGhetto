package com.xxxyjade.hiphopghetto.controller.intecepter;

import com.xxxyjade.hiphopghetto.common.constant.JwtClaimsConstant;
import com.xxxyjade.hiphopghetto.common.property.JwtProperties;
import com.xxxyjade.hiphopghetto.util.JwtUtil;
import com.xxxyjade.hiphopghetto.util.ThreadUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 非 Controller 方法放行
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        // 从请求头中获取令牌
        String token = request.getHeader(jwtProperties.getTokenName());

        // 校验令牌
        try {
            log.info("jwt校验:{}", token);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getSecretKey(), token);
            Long id = Long.valueOf(claims.get(JwtClaimsConstant.USER_ID).toString());
            log.info("当前用户id:{}", id);
            ThreadUtil.setId(id);
            return true;
        } catch (Exception ex) {
            response.setStatus(401);
            return false;
        }
    }
}
