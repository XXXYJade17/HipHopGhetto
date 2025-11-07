package com.xxxyjade.hiphopghetto.controller;

import com.xxxyjade.hiphopghetto.common.result.Result;
import com.xxxyjade.hiphopghetto.service.CollectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Tag(name = "收藏服务")
@Slf4j
public class CollectController {

    @Autowired
    private CollectService collectService;

    @GetMapping("/collect/{id}")
    @Operation(summary = "是否收藏")
    public Result<Boolean> select(@PathVariable("id") Long resourceId) {
        return Result.success(collectService.select(resourceId));
    }

    @PutMapping("/collect/{id}")
    @Operation(summary = "收藏")
    public Result<Void> collect(@PathVariable("id") Long resourceId) {
        collectService.collect(resourceId);
        return Result.success();
    }

    @DeleteMapping("/collect/{id}")
    @Operation(summary = "取消收藏")
    public Result<Void> cancel(@PathVariable("id") Long resourceId) {
        collectService.cancel(resourceId);
        return Result.success();
    }

}
