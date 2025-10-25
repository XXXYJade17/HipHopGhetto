package com.xxxyjade.hiphopghetto.service;

import com.xxxyjade.hiphopghetto.common.pojo.dto.UserLoginDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.UserRegisterDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.UserUpdateDTO;
import com.xxxyjade.hiphopghetto.common.pojo.vo.UserLoginVO;
import com.xxxyjade.hiphopghetto.common.pojo.vo.UserRegisterVO;
import com.xxxyjade.hiphopghetto.common.pojo.vo.UserVO;

public interface UserService {
    /**
     * 用户注册
     * @param userRegisterDTO 用户注册 DTO
     * @return 用户注册与登录 VO
     */
    UserRegisterVO register(UserRegisterDTO userRegisterDTO);

    /**
     * 用户登录
     * @param userLoginDTO 用户登录 DTO
     * @return 用户注册与登录 VO
     */
    UserLoginVO login(UserLoginDTO userLoginDTO);

    /**
     * 根据 Id 查询用户信息
     * @param id 用户 Id
     * @return 用户 VO
     */
    UserVO get(Long id);

    /**
     * 更新用户信息
     * @param userUpdateDTO 用户信息更新DTO
     * @return null
     */
    Void update(UserUpdateDTO userUpdateDTO);

    /**
     * 根据 Id 注销用户
     * @param id 用户Id
     * @return null
     */
    Void delete(Long id);

}
