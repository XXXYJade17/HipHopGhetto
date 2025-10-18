package com.xxxyjade.hiphopghetto.controller;


import com.xxxyjade.hiphopghetto.common.pojo.dto.ScoreCreateDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.ScoreDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.PageQueryDTO;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Score;
import com.xxxyjade.hiphopghetto.common.pojo.result.Result;
import com.xxxyjade.hiphopghetto.common.pojo.vo.ScoreDetailVO;
import com.xxxyjade.hiphopghetto.common.pojo.vo.PageVO;
import com.xxxyjade.hiphopghetto.service.ScoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/score")
@Tag(name = "评分服务")
@Slf4j
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    @Operation(summary = "创建评分")
    @PostMapping("/create")
    public Result<Void> creat(@Valid @RequestBody ScoreCreateDTO scoreCreateDTO) {
        log.info("ScoreCreateDTO:{}", scoreCreateDTO);
        return Result.success(scoreService.create(scoreCreateDTO));
    }

    @Operation(summary = "打分")
    @PostMapping("/vote")
    public Result<Void> vote(@RequestBody ScoreDTO scoreDTO) {
        log.info("ScoreDTO:{}", scoreDTO);
        return Result.success(scoreService.vote(scoreDTO));
    }

    @Operation(summary = "分页查询")
    @PostMapping("/page")
    public Result<PageVO<Score>> page(@RequestBody PageQueryDTO pageQueryDTO) {
        log.info("ScorePageDTO:{}", pageQueryDTO);
        return Result.success(scoreService.page(pageQueryDTO));
    }

    @Operation(summary = "详情查询")
    @GetMapping("/detail/{scoreId}")
    public Result<ScoreDetailVO> detail(@PathVariable("scoreId") Long scoreId) {
        log.info("ScoreId:{}", scoreId);
        return Result.success(scoreService.detail(scoreId));
    }

}
