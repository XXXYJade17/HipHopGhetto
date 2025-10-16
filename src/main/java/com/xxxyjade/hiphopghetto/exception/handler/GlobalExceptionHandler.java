package com.xxxyjade.hiphopghetto.exception.handler;

import com.xxxyjade.hiphopghetto.common.pojo.result.Result;
import com.xxxyjade.hiphopghetto.exception.BaseException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 业务异常
     * @param request
     * @param baseException
     * @return
     */
    @ExceptionHandler(value = BaseException.class)
    public Result<String> toolkitExceptionHandler(HttpServletRequest request, BaseException baseException) {
        log.error("业务异常 method : {} url : {} query : {} ",
                request.getMethod(),
                request.getRequestURL().toString(),
                request.getQueryString(),
                baseException);
        return Result.error(baseException.getCode(), baseException.getMessage());
    }

    /**
     * 拦截未捕获异常
     */
    @ExceptionHandler(value = Throwable.class)
    public Result<String> defaultErrorHandler(HttpServletRequest request, Throwable throwable) {
        log.error("全局异常 method : {} url : {} query : {} ",
                request.getMethod(),
                request.getRequestURL().toString(),
                request.getQueryString(),
                throwable);
        return Result.error();
    }

}
