package com.xxxyjade.hiphopghetto.controller;

import com.xxxyjade.hiphopghetto.common.pojo.dto.UserLoginDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.UserRegisterDTO;
import com.xxxyjade.hiphopghetto.common.pojo.result.Result;
import com.xxxyjade.hiphopghetto.common.pojo.vo.UserRegisterLoginVO;
import com.xxxyjade.hiphopghetto.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Tag(name = "用户服务")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary  = "注册")
    @PostMapping("/register")
    public Result<UserRegisterLoginVO> register(@Valid @RequestBody UserRegisterDTO userRegisterDTO) {
        log.info("UserRegisterDTO: {}",userRegisterDTO);
        return Result.success(userService.register(userRegisterDTO));
    }

    @Operation(summary = "登录")
    @PostMapping("/login")
    public Result<UserRegisterLoginVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("UserLoginDTO: {}",userLoginDTO);
        return Result.success(userService.login(userLoginDTO));
    }

//    @Operation(summary = "修改")
//    @PostMapping("/update")
//    public Result<Boolean> update(@RequestBody UserUpdateDTO userUpdateDTO) {
//        return Result.success();
//    }

}
