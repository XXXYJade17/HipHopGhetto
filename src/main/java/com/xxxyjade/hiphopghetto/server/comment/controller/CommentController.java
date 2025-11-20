package com.xxxyjade.hiphopghetto.server.comment.controller;

import com.xxxyjade.hiphopghetto.common.constant.ResourceType;
import com.xxxyjade.hiphopghetto.model.enums.SortType;
import com.xxxyjade.hiphopghetto.model.dto.CommentDTO;
import com.xxxyjade.hiphopghetto.model.dto.CommentRequestDTO;
import com.xxxyjade.hiphopghetto.model.dto.LikeDTO;
import com.xxxyjade.hiphopghetto.model.dto.PageQueryDTO;
import com.xxxyjade.hiphopghetto.model.vo.CommentVO;
import com.xxxyjade.hiphopghetto.model.vo.PageVO;
import com.xxxyjade.hiphopghetto.common.result.Result;
import com.xxxyjade.hiphopghetto.server.comment.service.CommentService;
import com.xxxyjade.hiphopghetto.server.like.service.LikeService;
import com.xxxyjade.hiphopghetto.util.ThreadUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/comments")
@Tag(name = "CommentController", description = "评论相关接口")
@AllArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;
    private final LikeService likeService;

    @GetMapping("/{parentId}")
    @Operation(summary = "（条件）分页查询评论")
    public Result<PageVO<CommentVO>> page(
            @PathVariable("parentId") Long parentId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size,
            @RequestParam(required = false) SortType sortType) {
        PageQueryDTO pageQueryDTO = PageQueryDTO.builder()
                .parentId(parentId)
                .page(page)
                .size(size)
                .sortType(sortType)
                .build();
        log.info("分页查询评论:{}", pageQueryDTO);
        PageVO<CommentVO> pageVO = commentService.page(pageQueryDTO);
        Map<Long, Boolean> userLikes = likeService.selectBatch(
                ThreadUtil.getUserId(),
                pageVO.getData().stream().map(CommentVO::getId).toList()
        );
        pageVO.getData().forEach(commentVO -> commentVO.setIsLiked(userLikes.get(commentVO.getId())));
        return Result.success(pageVO);
    }

    @PostMapping("/{parentId}")
    @Operation(summary = "创建评论")
    public Result<Void> create(
            @PathVariable("parentId") Long parentId,
            @RequestBody CommentRequestDTO commentRequestDTO) {
        CommentDTO commentDTO = CommentDTO.builder()
                .parentId(parentId)
                .content(commentRequestDTO.getContent())
                .build();
        log.info("创建评论:{}", commentDTO);
        commentService.create(commentDTO);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除评论")
    public Result<Void> delete(
            @PathVariable("id") Long id,
            @RequestParam("parentId") Long parentId) {
        log.info("删除评论:{},{}", id, parentId);
        commentService.delete(id, parentId);
        return Result.success();
    }

    @PostMapping("/{id}/like")
    @Operation(summary = "点赞评论")
    public Result<Void> like(@PathVariable("id") Long id) {
        LikeDTO likeDTO = LikeDTO.builder()
                .targetId(id)
                .targetType(ResourceType.COMMENT)
                .build();
        log.info("点赞评论:{}", likeDTO);
        likeService.like(likeDTO);
        return Result.success();
    }

    @DeleteMapping("/{id}/like")
    @Operation(summary = "取消点赞评论")
    public Result<Void> unlike(@PathVariable("id") Long id) {
        LikeDTO likeDTO = LikeDTO.builder()
                .targetId(id)
                .targetType(ResourceType.COMMENT)
                .build();
        log.info("取消点赞评论:{}", likeDTO);
        likeService.unlike(likeDTO);
        return Result.success();
    }

}
