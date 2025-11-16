package com.xxxyjade.hiphopghetto.controller;

import com.xxxyjade.hiphopghetto.common.pojo.dto.RatingCreateDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.RatingUpdateDTO;
import com.xxxyjade.hiphopghetto.common.result.Result;
import com.xxxyjade.hiphopghetto.service.RatingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Tag(name = "评分服务")
@Slf4j
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @GetMapping("/score/{id}")
    @Operation(summary = "查询评分")
    public Result<Integer> selectRating(@PathVariable("id") Long targetId) {
        return Result.success(ratingService.select(targetId));
    }

    @PostMapping("/score")
    @Operation(summary = "创建评分")
    public Result<Void> createRating(@RequestBody RatingCreateDTO ratingCreateDTO) {
        ratingService.create(ratingCreateDTO);
        return Result.success();
    }

    @PutMapping("/score")
    @Operation(summary = "修改评分")
    public Result<Void> updateRating(@RequestBody RatingUpdateDTO ratingUpdateDTO) {
        ratingService.update(ratingUpdateDTO);
        return Result.success();
    }

}
