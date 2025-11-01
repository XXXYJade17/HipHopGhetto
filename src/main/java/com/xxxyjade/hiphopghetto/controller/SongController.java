package com.xxxyjade.hiphopghetto.controller;

import com.xxxyjade.hiphopghetto.common.pojo.dto.PageQueryDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.SongScoreDTO;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Song;
import com.xxxyjade.hiphopghetto.common.result.Result;
import com.xxxyjade.hiphopghetto.common.pojo.vo.SongScoreVO;
import com.xxxyjade.hiphopghetto.common.pojo.vo.SongInfoVO;
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

    @Operation(summary = "查询")
    @PostMapping("/songs")
    public Result<List<Song>> page(@RequestBody PageQueryDTO pageQueryDTO) {
        return Result.success(songService.page(pageQueryDTO));
    }

    @Operation(summary = "详情")
    @GetMapping("/song/{id}")
    public Result<SongInfoVO> info(@PathVariable("id") Long id) {
        return Result.success(songService.info(id));
    }

    @Operation(summary = "评分")
    @PostMapping("/song/score")
    public Result<Void> score(@RequestBody SongScoreDTO songScoreDTO) {
        songService.score(songScoreDTO);
        return Result.success();
    }

    @Operation(summary = "评分是否存在")
    @GetMapping("/song/score/{id}")
    public Result<Integer> hasScore(@PathVariable("id") Long id) {
        return Result.success(songService.hasScore(id));
    }

}
