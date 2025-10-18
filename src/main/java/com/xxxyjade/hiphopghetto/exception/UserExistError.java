package com.xxxyjade.hiphopghetto.exception;

import com.xxxyjade.hiphopghetto.common.enums.BaseCode;

public class UserExistError extends BaseException{

    public UserExistError() {
        super(BaseCode.USER_EXIST);
    }

}
