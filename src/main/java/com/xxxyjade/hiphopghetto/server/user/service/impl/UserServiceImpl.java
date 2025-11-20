package com.xxxyjade.hiphopghetto.server.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xxxyjade.hiphopghetto.common.constant.JwtClaims;
import com.xxxyjade.hiphopghetto.model.enums.AccountType;
import com.xxxyjade.hiphopghetto.model.dto.UserLoginDTO;
import com.xxxyjade.hiphopghetto.model.dto.UserRegisterDTO;
import com.xxxyjade.hiphopghetto.model.entity.User;
import com.xxxyjade.hiphopghetto.model.entity.UserInfo;
import com.xxxyjade.hiphopghetto.model.vo.UserVO;
import com.xxxyjade.hiphopghetto.common.property.JwtProperties;
import com.xxxyjade.hiphopghetto.exception.HipHopGhettoFrameException;
import com.xxxyjade.hiphopghetto.server.user.mapper.UserInfoMapper;
import com.xxxyjade.hiphopghetto.server.user.mapper.UserMapper;
import com.xxxyjade.hiphopghetto.server.user.service.UserMessageService;
import com.xxxyjade.hiphopghetto.server.user.service.UserService;
import com.xxxyjade.hiphopghetto.util.JwtUtil;
import com.xxxyjade.hiphopghetto.util.PasswordUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private JwtProperties jwtProperties;
    private UserInfoMapper userInfoMapper;
    private UserMessageService userMessageService;
    private UserMapper userMapper;

    /**
     * 用户注册
     */
    public UserVO register(UserRegisterDTO userRegisterDTO) {
        // 提取参数
        String username = userRegisterDTO.getUsername();
        String phone = userRegisterDTO.getPhone();
        String email = userRegisterDTO.getEmail();
        String password = userRegisterDTO.getPassword();
        String confirmPassword = userRegisterDTO.getConfirmPassword();

        if (email.isBlank() && phone.isBlank()) {
            // 邮箱、手机号均不存在
            throw new HipHopGhettoFrameException("邮箱、手机号均不存在！");
        }

        if (!password.equals(confirmPassword)) {
            // 两次密码输入不一致
            throw new HipHopGhettoFrameException("两次密码输入不一致！");
        }

        if (userMapper.existByUsername(username)) {
            // 用户已存在
            throw new HipHopGhettoFrameException("用户已存在");
        }

        // 密码加密
        String encryptedPassword = PasswordUtil.encrypt(password);

        // 构造实体
        User user = User.builder()
                .username(username)
                .phone(phone)
                .email(email)
                .password(encryptedPassword)
                .build();

        // 插入数据
        userMapper.insert(user);

        userMessageService.sendUserInfoCreateMessage(user);

        // 生成令牌
        Map<String,Object> claims = new HashMap<>();
        claims.put(JwtClaims.USER_ID, user.getId());
        String token = JwtUtil.createJWT(jwtProperties.getSecretKey(), jwtProperties.getTtl(), claims);

        // 构造返回VO
        return UserVO.builder()
                .id(user.getId())
                .token(token)
                .build();
    }

    /**
     * 用户登录
     */
    public UserVO login(UserLoginDTO userLoginDTO) {
        // 判断账户类型
        String account = userLoginDTO.getAccount();
        AccountType accountType = AccountType.getAccountType(account);
        User user = new User();
        switch (accountType) {
            case EMAIL -> user.setEmail(account);
            case PHONE -> user.setPhone(account);
            case USERNAME -> user.setUsername(account);
        }

        // 校验密码
        user = userMapper.selectOne(new QueryWrapper<>(user));

        if (user == null) {
            // 用户不存在
            throw new ResourceNotFoundException(BaseCode.USER_NOT_FOUND);
        }

        if (!PasswordUtil.verify(userLoginDTO.getPassword(), user.getPassword())) {
            // 密码验证错误
            throw new AuthException(BaseCode.VERIFY_ERROR);
        }

        // 生成令牌
        Map<String,Object> claims = new HashMap<>();
        claims.put(JwtClaims.USER_ID, user.getId());
        String token = JwtUtil.createJWT(jwtProperties.getSecretKey(), jwtProperties.getTtl(), claims);

        return UserVO.builder()
                .id(user.getId())
                .token(token)
                .build();
    }

//    /**
//     * 根据 Id 获取用户信息
//     */
//    public UserInfoVO info(Long id) {
//        UserInfo userInfo = userInfoMapper.selectById(id);
//        if (userInfo == null) {
//            // 用户不存在
//            throw new HipHopGhettoFrameException("用户不存在！");
//        }
//        UserInfoVO userVO = new UserInfoVO();
//        BeanUtils.copyProperties(userInfo, userVO);
//        return userVO;
//    }
//
//    /**
//     * 更新用户信息
//     * @param userUpdateDTO 用户信息更新DTO
//     */
//    public void update(UserUpdateDTO userUpdateDTO) {
//        User user = new User();
//        BeanUtils.copyProperties(userUpdateDTO, user);
//        userMapper.update(user, null);
//    }
//
//    public void delete(Long id) {
//        userMapper.deleteById(id);
//    }

    /**
     * 创建用户信息
     */
    public void createInfo(User user) {
        userInfoMapper.insert(
                UserInfo.builder()
                        .id(user.getId())
                        .nickname(user.getUsername())
                        .build()
        );
    }

}
