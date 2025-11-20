package com.xxxyjade.hiphopghetto.server.topic.controller;

import com.xxxyjade.hiphopghetto.common.constant.ResourceType;
import com.xxxyjade.hiphopghetto.model.enums.SortType;
import com.xxxyjade.hiphopghetto.model.dto.LikeDTO;
import com.xxxyjade.hiphopghetto.model.dto.PageQueryDTO;
import com.xxxyjade.hiphopghetto.model.dto.TopicDTO;
import com.xxxyjade.hiphopghetto.model.vo.PageVO;
import com.xxxyjade.hiphopghetto.model.vo.TopicDetailVO;
import com.xxxyjade.hiphopghetto.model.vo.TopicVO;
import com.xxxyjade.hiphopghetto.common.result.Result;
import com.xxxyjade.hiphopghetto.server.like.service.LikeService;
import com.xxxyjade.hiphopghetto.server.topic.service.TopicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/topics")
@Tag(name = "TopicController", description = "话题相关接口")
@AllArgsConstructor
@Slf4j
public class TopicController {

    private final TopicService topicService;
    private final LikeService likeService;

    @GetMapping
    @Operation(summary = "（条件）分页查询话题")
    public Result<PageVO<TopicVO>> page(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size,
            @RequestParam(required = false) SortType sortType) {
        PageQueryDTO pageQueryDTO = PageQueryDTO.builder()
                .page(page)
                .size(size)
                .sortType(sortType)
                .build();
        log.info("分页查询:{}", pageQueryDTO);
        return Result.success(topicService.page(pageQueryDTO));
    }

    @GetMapping("/{id}")
    @Operation(summary = "话题详情")
    public Result<TopicDetailVO> detail(@PathVariable Long id) {
        log.info("话题详情:{}",id);
        return Result.success(topicService.detail(id));
    }

    @PostMapping
    @Operation(summary = "创建话题")
    public Result<Void> create(@RequestBody TopicDTO topicDTO) {
        log.info("创建话题:{}", topicDTO);
        topicService.create(topicDTO);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除话题")
    public Result<Void> delete(@PathVariable Long id) {
        log.info("删除话题:{}",id);
        topicService.delete(id);
        return Result.success();
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新话题")
    public Result<Void> update(
            @PathVariable("id") Long id,
            @RequestBody TopicDTO topicDTO) {
        topicDTO.setId(id);
        log.info("更新话题:{}", topicDTO);
        topicService.update(topicDTO);
        return Result.success();
    }

    @PostMapping("/{id}/like")
    @Operation(summary = "点赞话题")
    public Result<Void> like(@PathVariable("id") Long id) {
        LikeDTO likeDTO = LikeDTO.builder()
                .targetId(id)
                .targetType(ResourceType.TOPIC)
                .build();
        log.info("点赞话题:{}", likeDTO);
        likeService.like(likeDTO);
        return Result.success();
    }

    @DeleteMapping("/{id}/like")
    @Operation(summary = "取消点赞话题")
    public Result<Void> unlike(@PathVariable("id") Long id) {
        LikeDTO likeDTO = LikeDTO.builder()
                .targetId(id)
                .targetType(ResourceType.TOPIC)
                .build();
        log.info("取消点赞话题:{}", likeDTO);
        likeService.unlike(likeDTO);
        return Result.success();
    }

}
