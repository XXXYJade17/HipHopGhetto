package com.xxxyjade.hiphopghetto.controller;

import com.xxxyjade.hiphopghetto.common.pojo.dto.PageQueryDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.SongScoreDTO;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Song;
import com.xxxyjade.hiphopghetto.common.result.Result;
import com.xxxyjade.hiphopghetto.common.pojo.vo.SongScoreVO;
import com.xxxyjade.hiphopghetto.common.pojo.vo.SongVO;
import com.xxxyjade.hiphopghetto.service.SongService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "歌曲服务")
@Slf4j
public class SongController {

    @Autowired
    private SongService songService;

//    @Operation(summary = "查询")
//    @PostMapping("/songs")
//    public Result<PageVO<Song>> page(@RequestBody PageQueryDTO pageQueryDTO) {
//        return Result.success(songService.page(pageQueryDTO));
//
//    }

    @Operation(summary = "详情")
    @GetMapping("/song/{id}")
    public Result<SongVO> info(@PathVariable("id") Long id) {
        return Result.success(songService.info(id));
    }

    @Operation(summary = "评分")
    @PostMapping("/song/score")
    public Result<Void> score(@RequestBody SongScoreDTO songScoreDTO) {
        songService.score(songScoreDTO);
        return Result.success();
    }

    @Operation(summary = "获取评分")
    @GetMapping("/song/score/{id}")
    public Result<SongScoreVO> getScore(@PathVariable("id") Long id) {
        return Result.success(songService.getScore(id));
    }

}
