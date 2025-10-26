package com.xxxyjade.hiphopghetto.controller;

import com.xxxyjade.hiphopghetto.common.pojo.dto.UserLoginDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.UserRegisterDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.UserUpdateDTO;
import com.xxxyjade.hiphopghetto.common.result.Result;
import com.xxxyjade.hiphopghetto.common.pojo.vo.UserLoginVO;
import com.xxxyjade.hiphopghetto.common.pojo.vo.UserRegisterVO;
import com.xxxyjade.hiphopghetto.common.pojo.vo.UserVO;
import com.xxxyjade.hiphopghetto.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Tag(name = "用户服务")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary  = "注册")
    @PostMapping("/register")
    public Result<UserRegisterVO> register(@Valid @RequestBody UserRegisterDTO userRegisterDTO) {
        log.info("UserRegisterDTO: {}",userRegisterDTO);
        return Result.success(userService.register(userRegisterDTO));
    }

    @Operation(summary = "登录")
    @PostMapping("/login")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("UserLoginDTO: {}",userLoginDTO);
        return Result.success(userService.login(userLoginDTO));
    }

    @Operation(summary = "获取信息")
    @GetMapping("/{id}")
    public Result<UserVO> get(@PathVariable("id") Long id) {
        log.info("Id:{}",id);
        return Result.success(userService.get(id));
    }

    @Operation(summary = "更新信息")
    @PutMapping
    public Result<Void> update(@RequestBody UserUpdateDTO userUpdateDTO) {
        log.info("UserUpdateDTO:{}", userUpdateDTO);
        return Result.success(userService.update(userUpdateDTO));
    }

    @Operation(summary = "注销")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable("id") Long id) {
        log.info("Id:{}",id);
        return Result.success(userService.delete(id));
    }

}
