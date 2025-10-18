package com.xxxyjade.hiphopghetto.common.pojo.result;

import com.xxxyjade.hiphopghetto.common.enums.BaseCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Schema(title="数据响应规范结构")
public class Result<T> {
    @Schema(name ="状态码", type ="Integer", description ="0-成功 其余-失败")
    private Integer code;

    @Schema(name ="错误信息", type ="String")
    private String message;

    @Schema(name ="响应数据")
    private T data;

    private Result() {}

    public static <T> Result<T> error(BaseCode baseCode) {
        Result<T> result = new Result<>();
        result.code = baseCode.getCode();
        result.message = baseCode.getMsg();
        return result;
    }

    public static <T> Result<T> error(BaseCode baseCode, T data) {
        Result<T> result = new Result<>();
        result.code = baseCode.getCode();
        result.message = baseCode.getMsg();
        result.data = data;
        return result;
    }

    public static <T> Result<T> error(Integer code, String msg) {
        Result<T> result = new Result<>();
        result.code = code;
        result.message = msg;
        return result;
    }

    public static <T> Result<T> error() {
        Result<T> result = new Result<>();
        result.code = -1;
        result.message = "系统错误，请稍后重试!";
        return result;
    }

    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.code = 0;
        result.message = "响应成功";
        return result;
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<T>();
        result.code = 0;
        result.message = "响应成功";
        result.data = data;
        return result;
    }
}
