package com.xxxyjade.hiphopghetto.controller;

import com.xxxyjade.hiphopghetto.common.pojo.dto.CommentDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.CommentPageQueryDTO;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Comment;
import com.xxxyjade.hiphopghetto.common.pojo.vo.PageVO;
import com.xxxyjade.hiphopghetto.common.result.Result;
import com.xxxyjade.hiphopghetto.service.CollectService;
import com.xxxyjade.hiphopghetto.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Tag(name = "评论服务")
@Slf4j
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/comments")
    @Operation(summary = "（条件）分页查询专辑/歌曲评论")
    public Result<PageVO<Comment>> page(@RequestBody CommentPageQueryDTO commentPageQueryDTO) {
        return Result.success(commentService.page(commentPageQueryDTO));
    }

    @PostMapping("/comment")
    @Operation(summary = "创建评论")
    public Result<Void> comment(@RequestBody CommentDTO commentDTO) {
        commentService.comment(commentDTO);
        return Result.success();
    }

    @DeleteMapping("/comment/{id}")
    @Operation(summary = "删除评论")
    public Result<Void> delete(@PathVariable("id") Long resourceId) {
        commentService.delete(resourceId);
        return Result.success();
    }

}
