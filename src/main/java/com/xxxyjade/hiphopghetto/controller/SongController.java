package com.xxxyjade.hiphopghetto.controller;

import com.xxxyjade.hiphopghetto.common.pojo.dto.*;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Comment;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Song;
import com.xxxyjade.hiphopghetto.common.pojo.vo.PageVO;
import com.xxxyjade.hiphopghetto.common.result.Result;
import com.xxxyjade.hiphopghetto.common.pojo.vo.SongInfoVO;
import com.xxxyjade.hiphopghetto.service.CommentService;
import com.xxxyjade.hiphopghetto.service.SongService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "歌曲服务")
@Slf4j
public class SongController {

    @Autowired
    private SongService songService;
    @Autowired
    private CommentService commentService;

    @Operation(summary = "（条件）分页查询歌曲")
    @PostMapping("/songs")
    public Result<PageVO<Song>> page(@RequestBody PageQueryDTO pageQueryDTO) {
        return Result.success(songService.page(pageQueryDTO));
    }

    @Operation(summary = "查询歌曲详情")
    @GetMapping("/song/{id}")
    public Result<SongInfoVO> info(@PathVariable("id") Long id) {
        return Result.success(songService.info(id));
    }

    @Operation(summary = "创建/修改歌曲评分")
    @PostMapping("/song/score")
    public Result<Void> score(@RequestBody ScoreDTO scoreDTO) {
        songService.score(scoreDTO);
        return Result.success();
    }

    @Operation(summary = "收藏/取消收藏歌曲")
    @GetMapping("/song/{id}/collect")
    public Result<Void> collect(@PathVariable("id") Long id) {
        songService.collect(id);
        return Result.success();
    }

    @Operation(summary = "（条件）分页查询歌曲评论")
    @PostMapping("/song/comments")
    public Result<PageVO<Comment>> commentPage(@RequestBody CommentPageQueryDTO commentPageQueryDTO) {
        return Result.success(commentService.commentPage(commentPageQueryDTO));
    }

    @Operation(summary = "创建评论")
    @PostMapping("/song/comment")
    public Result<Void> comment(@RequestBody CommentDTO commentDTO) {
        commentService.comment(commentDTO);
        return Result.success();
    }

    @Operation(summary = "删除评论")
    @DeleteMapping("/song/comment/{id}")
    public Result<Void> deleteComment(@PathVariable("id") Long id) {
        commentService.deleteComment(id);
        return Result.success();
    }

}
