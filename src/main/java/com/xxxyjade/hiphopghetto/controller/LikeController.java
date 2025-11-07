package com.xxxyjade.hiphopghetto.controller;

import com.xxxyjade.hiphopghetto.common.result.Result;
import com.xxxyjade.hiphopghetto.service.CollectService;
import com.xxxyjade.hiphopghetto.service.LikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Tag(name = "点赞服务")
@Slf4j
public class LikeController {

    @Autowired
    private LikeService likeService;

    @GetMapping("/like/{id}")
    @Operation(summary = "是否点赞")
    public Result<Boolean> select(@PathVariable("id") Long resourceId) {
        return Result.success(likeService.select(resourceId));
    }

    @PutMapping("/like/{id}")
    @Operation(summary = "点赞")
    public Result<Void> like(@PathVariable("id") Long resourceId) {
        likeService.like(resourceId);
        return Result.success();
    }

    @DeleteMapping("/like/{id}")
    @Operation(summary = "取消点赞")
    public Result<Void> cancel(@PathVariable("id") Long resourceId) {
        likeService.cancel(resourceId);
        return Result.success();
    }

}
