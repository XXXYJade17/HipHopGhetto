package com.xxxyjade.hiphopghetto.model.enums;

public enum BaseCode {
    SUCCESS(200, "响应成功"),
    DATABASE_EXCEPTION(1, "数据库异常"),
    ARGUMENT_ERROR(2, "参数校验错误"),
    VERIFY_ERROR(3, "密码校验错误"),
    USER_EXIST(4, "用户已存在"),
    PASSWORDS_DIFFERENT(5, "两次密码不一致"),
    USER_EMPTY(6, "用户不存在");

    private final Integer code;

    private String msg = "";

    BaseCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    BaseCode(String msg) {
        this.code = -1;
        this.msg = msg;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg == null ? "" : this.msg;
    }

    public static String getMsg(Integer code) {
        for (BaseCode re : BaseCode.values()) {
            if (re.code.intValue() == code.intValue()) {
                return re.msg;
            }
        }
        return "";
    }

    public static BaseCode getCode(Integer code) {
        for (BaseCode re : BaseCode.values()) {
            if (re.code.intValue() == code.intValue()) {
                return re;
            }
        }
        return null;
    }
}
