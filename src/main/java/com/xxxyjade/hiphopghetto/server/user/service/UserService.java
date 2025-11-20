package com.xxxyjade.hiphopghetto.server.user.service;

import com.xxxyjade.hiphopghetto.model.dto.UserLoginDTO;
import com.xxxyjade.hiphopghetto.model.dto.UserRegisterDTO;
import com.xxxyjade.hiphopghetto.model.dto.UserUpdateDTO;
import com.xxxyjade.hiphopghetto.model.entity.User;
import com.xxxyjade.hiphopghetto.model.vo.UserInfoVO;
import com.xxxyjade.hiphopghetto.model.vo.UserVO;

public interface UserService {
    /**
     * 用户注册
     */
    UserVO register(UserRegisterDTO userRegisterDTO);

    /**
     * 用户登录
     */
    UserVO login(UserLoginDTO userLoginDTO);

//    /**
//     * 查询用户信息
//     */
//    UserInfoVO info(Long id);
//
//    /**
//     * 更新用户信息
//     */
//    void update(UserUpdateDTO userUpdateDTO);
//
//    /**
//     * 注销用户
//     */
//    void delete(Long id);

    /**
     * 创建用户信息
     */
    void createInfo(User user);

}
