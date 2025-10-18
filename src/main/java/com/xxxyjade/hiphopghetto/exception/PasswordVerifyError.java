package com.xxxyjade.hiphopghetto.exception;

import com.xxxyjade.hiphopghetto.common.enums.BaseCode;

public class PasswordVerifyError extends BaseException{

    public PasswordVerifyError() {
        super(BaseCode.VERIFY_ERROR);
    }

}
