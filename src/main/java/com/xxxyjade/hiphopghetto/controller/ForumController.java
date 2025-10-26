package com.xxxyjade.hiphopghetto.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/forum")
@Tag(name = "论坛服务")
@Slf4j
public class ForumController {
//
//    @Autowired
//    private PostingService postingService;
//
//    @Operation(summary = "创建帖子")
//    @PostMapping("/create")
//    public Result<Void> createPosting(@Valid @RequestBody PostingCreateDTO postingCreateDTO) {
//        log.info("PostingCreateDTO:{}",postingCreateDTO);
//        return Result.success(postingService.createPosting(postingCreateDTO));
//    }
//
//    @Operation(summary = "分页查询帖子")
//    @PostMapping
//    public Result<PageVO<Posting>> page(@Valid @RequestBody PageQueryDTO pageQueryDTO) {
//        log.info("PageQueryDTO:{}", pageQueryDTO);
//        return Result.success(postingService.page(pageQueryDTO));
//    }


}
