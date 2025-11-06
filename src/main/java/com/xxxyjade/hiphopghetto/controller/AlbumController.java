package com.xxxyjade.hiphopghetto.controller;

import com.xxxyjade.hiphopghetto.common.pojo.dto.CommentPageQueryDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.ScoreDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.CommentDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.PageQueryDTO;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Album;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Comment;
import com.xxxyjade.hiphopghetto.common.pojo.vo.AlbumInfoVO;
import com.xxxyjade.hiphopghetto.common.pojo.vo.PageVO;
import com.xxxyjade.hiphopghetto.common.result.Result;
import com.xxxyjade.hiphopghetto.service.AlbumService;
import com.xxxyjade.hiphopghetto.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Tag(name = "专辑服务")
@Slf4j
public class AlbumController {

    @Autowired
    private AlbumService albumService;
    @Autowired
    private CommentService commentService;

    @Operation(summary = "（条件）分页查询专辑")
    @PostMapping("/albums")
    public Result<PageVO<Album>> page(@RequestBody PageQueryDTO pageQueryDTO) {
        return Result.success(albumService.page(pageQueryDTO));
    }

    @Operation(summary = "查询专辑详情")
    @GetMapping("/album/{id}")
    public Result<AlbumInfoVO> info(@PathVariable("id") Long id) {
        log.info("AlbumId:{}",id);
        return Result.success(albumService.info(id));
    }

    @Operation(summary = "创建/修改专辑评分")
    @PostMapping("/album/score")
    public Result<Void> score(@RequestBody ScoreDTO scoreDTO) {
        albumService.score(scoreDTO);
        return Result.success();
    }

    @Operation(summary = "收藏/取消收藏专辑")
    @GetMapping("/album/{id}/collect")
    public Result<Void> collect(@PathVariable("id") Long id) {
        albumService.collect(id);
        return Result.success();
    }

    @Operation(summary = "（条件）分页查询专辑评论")
    @PostMapping("/album/comments")
    public Result<PageVO<Comment>> commentPage(@RequestBody CommentPageQueryDTO commentPageQueryDTO) {
        return Result.success(commentService.commentPage(commentPageQueryDTO));
    }

    @Operation(summary = "创建评论")
    @PostMapping("/album/comment")
    public Result<Boolean> comment(@RequestBody CommentDTO commentDTO) {
        commentService.comment(commentDTO);
        return Result.success();
    }

    @Operation(summary = "删除评论")
    @DeleteMapping("/album/comment/{id}")
    public Result<Void> deleteComment(@PathVariable("id") Long id) {
        commentService.deleteComment(id);
        return Result.success();
    }

}
