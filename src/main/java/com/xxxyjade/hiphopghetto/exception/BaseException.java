package com.xxxyjade.hiphopghetto.exception;

import com.xxxyjade.hiphopghetto.common.enums.BaseCode;
import lombok.Data;

@Data
public class BaseException extends RuntimeException{

    private Integer code;

    private String message;

//    public BaseException(Integer code, String message) {
//        super(message);
//        this.code = code;
//        this.message = message;
//    }

    public BaseException(BaseCode baseCode) {
        super(baseCode.getMsg());
        this.code = baseCode.getCode();
        this.message = baseCode.getMsg();
    }

}
