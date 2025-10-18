package com.xxxyjade.hiphopghetto.exception.handler;

import com.xxxyjade.hiphopghetto.common.enums.BaseCode;
import com.xxxyjade.hiphopghetto.common.pojo.result.Result;
import com.xxxyjade.hiphopghetto.exception.ArgumentError;
import com.xxxyjade.hiphopghetto.exception.BaseException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 业务异常
     * @param request 请求实体
     * @param ex 异常
     * @return
     */
    @ExceptionHandler(BaseException.class)
    public Result<Void> toolkitExceptionHandler(HttpServletRequest request, BaseException ex) {
        log.error("业务异常 method : {} url : {} query : {} ",
                request.getMethod(),
                request.getRequestURL().toString(),
                request.getQueryString(),
                ex);
        return Result.error(ex.getCode(), ex.getMessage());
    }

    /**
     * 参数验证异常
     * @param request 请求实体
     * @param ex 参数校验异常
     * @return
     */
    @SneakyThrows
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<List<ArgumentError>> validExceptionHandler(HttpServletRequest request, MethodArgumentNotValidException ex) {
        log.error("参数验证异常 method : {} url : {} query : {} ",
                request.getMethod(),
                request.getRequestURL().toString(),
                request.getQueryString(),
                ex);
        List<ArgumentError> argumentErrorList =
                ex.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(fieldError -> new ArgumentError(
                                fieldError.getField(),
                                fieldError.getDefaultMessage()
                        )).collect(Collectors.toList());
        return Result.error(BaseCode.ARGUMENT_ERROR,argumentErrorList);
    }

    /**
     * 其余异常
     * @param request 请求实体
     * @param throwable 异常
     * @return
     */
    @ExceptionHandler(Throwable.class)
    public Result<String> defaultErrorHandler(HttpServletRequest request, Throwable throwable) {
        log.error("全局异常 method : {} url : {} query : {} ",
                request.getMethod(),
                request.getRequestURL().toString(),
                request.getQueryString(),
                throwable);
        return Result.error();
    }

}
