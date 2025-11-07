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

@RestController
@RequestMapping("/api")
@Tag(name = "歌曲服务")
@Slf4j
public class SongController {

    @Autowired
    private SongService songService;

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

}
