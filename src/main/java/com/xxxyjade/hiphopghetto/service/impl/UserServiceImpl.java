package com.xxxyjade.hiphopghetto.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxyjade.hiphopghetto.common.constant.JwtClaimsConstant;
import com.xxxyjade.hiphopghetto.common.enums.AccountType;
import com.xxxyjade.hiphopghetto.common.enums.BaseCode;
import com.xxxyjade.hiphopghetto.common.pojo.dto.UserLoginDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.UserRegisterDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.UserUpdateDTO;
import com.xxxyjade.hiphopghetto.common.pojo.entity.User;
import com.xxxyjade.hiphopghetto.common.pojo.vo.UserLoginVO;
import com.xxxyjade.hiphopghetto.common.pojo.vo.UserRegisterVO;
import com.xxxyjade.hiphopghetto.common.pojo.vo.UserVO;
import com.xxxyjade.hiphopghetto.common.property.JwtProperties;
import com.xxxyjade.hiphopghetto.exception.HipHopGhettoFrameException;
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
     */
    public UserRegisterVO register(UserRegisterDTO userRegisterDTO) {
        // 判断先后密码是否一致
        if (!userRegisterDTO.getPassword().equals(userRegisterDTO.getConfirmPassword())) {
            throw new HipHopGhettoFrameException(BaseCode.PASSWORDS_DIFFERENT);
        }
        // 判断用户名是否存在
        if (userMapper.existByUsername(userRegisterDTO.getUsername())) {
            throw new HipHopGhettoFrameException(BaseCode.USER_EXIST);
        }

        // 创建实体
        User user = new User();
        BeanUtils.copyProperties(userRegisterDTO,user);

        // 密码加密
        String encryptedPassword = PasswordUtil.encrypt(user.getPassword());
        user.setPassword(encryptedPassword);

        // 插入数据
        userMapper.insert(user);

        // 生成令牌
        Map<String,Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getUserId());
        String token = JwtUtil.createJWT(jwtProperties.getSecretKey(), jwtProperties.getTtl(), claims);

        // 构造返回VO
        return UserRegisterVO.builder()
                .id(user.getUserId())
                .token(token)
                .build();
    }

    /**
     * 用户登录
     */
    public UserLoginVO login(UserLoginDTO userLoginDTO) {
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
            throw new HipHopGhettoFrameException(BaseCode.USER_EMPTY);
        }

        if (!PasswordUtil.verify(userLoginDTO.getPassword(), user.getPassword())) {
            // 密码验证错误
            throw new HipHopGhettoFrameException(BaseCode.VERIFY_ERROR);
        }

        // 生成令牌
        Map<String,Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getUserId());
        String token = JwtUtil.createJWT(jwtProperties.getSecretKey(), jwtProperties.getTtl(), claims);

        return UserLoginVO.builder()
                .id(user.getUserId())
                .token(token)
                .build();
    }

    /**
     * 根据 Id 获取用户信息
     */
    public UserVO info(Long id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            // 用户不存在
            throw new HipHopGhettoFrameException(BaseCode.USER_EMPTY);
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    /**
     * 更新用户信息
     * @param userUpdateDTO 用户信息更新DTO
     */
    public void update(UserUpdateDTO userUpdateDTO) {
        User user = new User();
        BeanUtils.copyProperties(userUpdateDTO, user);
        userMapper.update(user, null);
    }

    public void delete(Long id) {
        userMapper.deleteById(id);
    }

}
