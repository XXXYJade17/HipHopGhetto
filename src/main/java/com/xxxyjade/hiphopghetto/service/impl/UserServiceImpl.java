package com.xxxyjade.hiphopghetto.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxyjade.hiphopghetto.common.pojo.dto.UserLoginDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.UserRegisterDTO;
import com.xxxyjade.hiphopghetto.common.pojo.entity.User;
import com.xxxyjade.hiphopghetto.mapper.UserMapper;
import com.xxxyjade.hiphopghetto.service.UserService;
import com.xxxyjade.hiphopghetto.util.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 用户 注册
     * @param userRegisterDTO 用户 注册DTO
     */
    public void register(UserRegisterDTO userRegisterDTO) {
        // 如果用户名和手机号均为 null，返回 false
        if (userRegisterDTO.getUsername().trim().isEmpty() && userRegisterDTO.getPhone().trim().isEmpty()) {
            // TODO 抛参数异常
        }
        // 创建实体
        User user = new User();
        BeanUtils.copyProperties(userRegisterDTO,user);
        // 密码加密
        String encryptedPassword = PasswordUtil.encrypt(user.getPassword());
        user.setPassword(encryptedPassword);
        // 插入数据
        userMapper.insert(user);

        // TODO 生成JWT令牌
    }

    /**
     * 用户登录
     * @param userLoginDTO 用户 登录DTO
     */
    public void login(UserLoginDTO userLoginDTO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        if (!userLoginDTO.getUsername().trim().isEmpty()) {
            queryWrapper.and(qw -> qw.eq("username", userLoginDTO.getUsername()));
        }

        if (!userLoginDTO.getPhone().trim().isEmpty()) {
            queryWrapper.or(qw -> qw.eq("phone", userLoginDTO.getPhone()));
        }

        User user = userMapper.selectOne(queryWrapper);
        String encryptedPassword = user.getPassword();

        if (!PasswordUtil.verify(userLoginDTO.getPassword(), encryptedPassword)) {
            //TODO 抛校验失败异常
        }
    }
}
