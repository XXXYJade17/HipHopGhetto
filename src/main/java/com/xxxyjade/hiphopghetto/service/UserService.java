package com.xxxyjade.hiphopghetto.service;

import com.xxxyjade.hiphopghetto.common.pojo.dto.UserLoginDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.UserRegisterDTO;
import com.xxxyjade.hiphopghetto.common.pojo.vo.UserRegisterLoginVO;

public interface UserService {
    /**
     * 用户注册
     * @param userRegisterDTO 用户注册 DTO
     * @return 用户注册与登录 VO
     */
    UserRegisterLoginVO register(UserRegisterDTO userRegisterDTO);

    /**
     * 用户登录
     * @param userLoginDTO 用户登录 DTO
     * @return 用户注册与登录 VO
     */
    UserRegisterLoginVO login(UserLoginDTO userLoginDTO);
}
