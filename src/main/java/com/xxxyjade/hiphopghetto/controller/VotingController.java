package com.xxxyjade.hiphopghetto.controller;


import com.xxxyjade.hiphopghetto.common.pojo.dto.VotingCreateDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.VotingDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.VotingPageDTO;
import com.xxxyjade.hiphopghetto.common.pojo.result.Result;
import com.xxxyjade.hiphopghetto.service.VotingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/activity")
@Tag(name = "评分服务")
@Slf4j
public class VotingController {

    @Autowired
    private VotingService votingService;

    @Operation(summary = "创建评分")
    @PostMapping("/create")
    public Result<Void> creat(@RequestBody VotingCreateDTO votingCreateDTO) {
        log.info("VotingCreateDTO:{}", votingCreateDTO);
        votingService.create(votingCreateDTO);
        return Result.success();
    }

    @Operation(summary = "打分")
    @PostMapping("/vote")
    public Result<Void> vote(@RequestBody VotingDTO votingDTO) {
        log.info("VotingDTO:{}", votingDTO);
        votingService.vote(votingDTO);
        return Result.success();
    }

    @Operation(summary = "分页查询")
    @PostMapping("/page")
    public Result page(@RequestBody VotingPageDTO votingPageDTO) {
        log.info("VotingPageDTO:{}",votingPageDTO);

        return null;
    }

}
