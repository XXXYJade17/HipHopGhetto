package com.xxxyjade.hiphopghetto.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxyjade.hiphopghetto.common.constant.JwtClaimsConstant;
import com.xxxyjade.hiphopghetto.common.enums.AccountType;
import com.xxxyjade.hiphopghetto.common.enums.BaseCode;
import com.xxxyjade.hiphopghetto.common.pojo.dto.UserLoginDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.UserRegisterDTO;
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
     * @param userRegisterDTO 用户注册 DTO
     * @return 用户注册 VO
     */
    public UserRegisterVO register(UserRegisterDTO userRegisterDTO) {
        // 判断用户名是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>()
                .eq("username", userRegisterDTO.getUsername());
        if (userMapper.exists(queryWrapper)) {
            throw new HipHopGhettoFrameException(BaseCode.USER_EXIST);
        }
        // 判断先后密码是否一致
        if (!userRegisterDTO.getPassword().trim().equals(userRegisterDTO.getConfirmPassword().trim())) {
            throw new HipHopGhettoFrameException(BaseCode.PASSWORDS_DIFFERENT);
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
        return UserRegisterVO.builder()
                .id(user.getId())
                .token(token)
                .build();
    }

    /**
     * 用户登录
     * @param userLoginDTO 用户 登录DTO
     * @return 用户注册与登录 VO
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
            throw new HipHopGhettoFrameException(BaseCode.USER_EMPTY);
        }

        if (!PasswordUtil.verify(userLoginDTO.getPassword(), user.getPassword())) {
            throw new HipHopGhettoFrameException(BaseCode.VERIFY_ERROR);
        }

        // 生成令牌
        Map<String,Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        String token = JwtUtil.createJWT(jwtProperties.getSecretKey(), jwtProperties.getTtl(), claims);

        ThreadUtil.setId(user.getId());

        return UserLoginVO.builder()
                .id(user.getId())
                .token(token)
                .build();
    }

    /**
     * 根据 Id 获取用户信息
     * @param id 用户 Id
     * @return 用户VO
     */
    public UserVO getById(Long id) {
        User user = userMapper.selectById(id);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

}
