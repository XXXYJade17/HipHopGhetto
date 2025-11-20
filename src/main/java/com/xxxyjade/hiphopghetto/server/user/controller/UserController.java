package com.xxxyjade.hiphopghetto.server.user.controller;

import com.xxxyjade.hiphopghetto.model.dto.UserLoginDTO;
import com.xxxyjade.hiphopghetto.model.dto.UserRegisterDTO;
import com.xxxyjade.hiphopghetto.common.result.Result;
import com.xxxyjade.hiphopghetto.model.vo.UserVO;
import com.xxxyjade.hiphopghetto.server.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Tag(name = "UserController", description = "用户相关接口")
@AllArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @Operation(summary  = "注册")
    @PostMapping("/register")
    public Result<UserVO> register(@Valid @RequestBody UserRegisterDTO userRegisterDTO) {
        log.info("用户注册:{}",userRegisterDTO);
        return Result.success(userService.register(userRegisterDTO));
    }

    @Operation(summary = "登录")
    @PostMapping("/login")
    public Result<UserVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("用户登录:{}",userLoginDTO);
        return Result.success(userService.login(userLoginDTO));
    }

//    @Operation(summary = "详情")
//    @GetMapping("/{id}")
//    public Result<UserInfoVO> info(@PathVariable("id") Long id) {
//        log.info("详情:{}",id);
//        return Result.success(userService.info(id));
//    }

//    @Operation(summary = "更新信息")
//    @PutMapping
//    public Result<Void> update(@RequestBody UserUpdateDTO userUpdateDTO) {
//        log.info("UserUpdateDTO:{}", userUpdateDTO);
//        userService.update(userUpdateDTO);
//        return Result.success();
//    }
//
//    @Operation(summary = "注销")
//    @DeleteMapping("/{id}")
//    public Result<Void> delete(@PathVariable("id") Long id) {
//        log.info("Id:{}",id);
//        userService.delete(id);
//        return Result.success();
//    }

}
