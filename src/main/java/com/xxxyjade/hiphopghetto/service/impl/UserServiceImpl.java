package com.xxxyjade.hiphopghetto.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxyjade.hiphopghetto.common.constant.JwtClaimsConstant;
import com.xxxyjade.hiphopghetto.common.enums.BaseCode;
import com.xxxyjade.hiphopghetto.common.pojo.dto.UserLoginDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.UserRegisterDTO;
import com.xxxyjade.hiphopghetto.common.pojo.entity.User;
import com.xxxyjade.hiphopghetto.common.pojo.vo.UserRegisterLoginVO;
import com.xxxyjade.hiphopghetto.common.property.JwtProperties;
import com.xxxyjade.hiphopghetto.exception.PasswordVerifyError;
import com.xxxyjade.hiphopghetto.exception.UserExistError;
import com.xxxyjade.hiphopghetto.mapper.UserMapper;
import com.xxxyjade.hiphopghetto.service.UserService;
import com.xxxyjade.hiphopghetto.util.JwtUtil;
import com.xxxyjade.hiphopghetto.util.PasswordUtil;
import com.xxxyjade.hiphopghetto.util.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private UserMapper userMapper;

    /**
     * 用户注册
     * @param userRegisterDTO 用户注册 DTO
     * @return 用户注册 VO
     */
    public UserRegisterLoginVO register(UserRegisterDTO userRegisterDTO) {
        // 判断用户名是否存在
        boolean isExist = userMapper.exists(
                new QueryWrapper<User>()
                        .eq("username", userRegisterDTO.getUsername())
        );
        if (!isExist) {
            throw new UserExistError();
        }

        // 创建实体
        User user = User.builder().build();
        BeanUtils.copyProperties(userRegisterDTO,user);

        // 密码加密
        String encryptedPassword = PasswordUtil.encrypt(user.getPassword());
        user.setPassword(encryptedPassword);

        // 插入数据
        userMapper.insert(user);

        // 生成令牌
        Map<String,Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        String token = JwtUtil.createJWT(jwtProperties.getSecretKey(), jwtProperties.getTtl(), claims);

        ThreadUtil.setId(user.getId());

        // 构造返回VO
        return UserRegisterLoginVO.builder()
                .token(token)
                .build();
    }

    /**
     * 用户登录
     * @param userLoginDTO 用户 登录DTO
     * @return 用户注册与登录 VO
     */
    public UserRegisterLoginVO login(UserLoginDTO userLoginDTO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        // 如果用户名不为空
        if (!userLoginDTO.getUsername().trim().isEmpty()) {
            queryWrapper.and(qw -> qw.eq("username", userLoginDTO.getUsername()));
        }

        // 如果手机号不为空
        if (!userLoginDTO.getPhone().trim().isEmpty()) {
            queryWrapper.or(qw -> qw.eq("phone", userLoginDTO.getPhone()));
        }

        // 查密码
        User user = userMapper.selectOne(queryWrapper);

        // 校验密码
        String encryptedPassword = user.getPassword();
        if (!PasswordUtil.verify(userLoginDTO.getPassword(), encryptedPassword)) {
            throw new PasswordVerifyError();
        }

        // 生成令牌
        Map<String,Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        String token = JwtUtil.createJWT(jwtProperties.getSecretKey(), jwtProperties.getTtl(), claims);

        ThreadUtil.setId(user.getId());

        return UserRegisterLoginVO.builder()
                .token(token)
                .build();
    }

}
