package com.xxxyjade.hiphopghetto.controller;

import com.xxxyjade.hiphopghetto.common.pojo.dto.ScoreDTO;
import com.xxxyjade.hiphopghetto.common.result.Result;
import com.xxxyjade.hiphopghetto.service.ScoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Tag(name = "评分服务")
@Slf4j
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    @GetMapping("/score/{id}")
    @Operation(summary = "查询评分")
    public Result<Integer> select(@PathVariable("id") Long resourceId) {
        return Result.success(scoreService.select(resourceId));
    }

    @PutMapping("/score")
    @Operation(summary = "评分")
    public Result<Void> collect(@RequestBody ScoreDTO scoreDTO) {
        scoreService.score(scoreDTO);
        return Result.success();
    }

}
