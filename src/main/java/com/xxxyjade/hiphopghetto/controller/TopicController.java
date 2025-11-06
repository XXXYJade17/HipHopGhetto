package com.xxxyjade.hiphopghetto.controller;

import com.xxxyjade.hiphopghetto.common.pojo.dto.PageQueryDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.TopicCreateDTO;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Topic;
import com.xxxyjade.hiphopghetto.common.pojo.vo.PageVO;
import com.xxxyjade.hiphopghetto.common.result.Result;
import com.xxxyjade.hiphopghetto.service.TopicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Tag(name = "讨论区")
@Slf4j
public class TopicController {

    @Autowired
    private TopicService topicService;

    @PostMapping("/topics")
    @Operation(summary = "（条件）分页查询话题信息")
    public Result<PageVO<Topic>> page(@RequestBody PageQueryDTO pageQueryDTO) {
        return Result.success(topicService.page(pageQueryDTO));
    }

    @PostMapping("/topic/create")
    @Operation(summary = "创建话题")
    public Result<Void> create(TopicCreateDTO topicCreateDTO) {
        topicService.create(topicCreateDTO);
        return Result.success();
    }

}
