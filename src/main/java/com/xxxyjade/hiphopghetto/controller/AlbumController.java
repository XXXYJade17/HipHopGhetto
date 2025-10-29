package com.xxxyjade.hiphopghetto.controller;

import com.xxxyjade.hiphopghetto.common.pojo.dto.AlbumScoreDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.PageQueryDTO;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Album;
import com.xxxyjade.hiphopghetto.common.pojo.vo.AlbumInfoVO;
import com.xxxyjade.hiphopghetto.common.result.Result;
import com.xxxyjade.hiphopghetto.common.pojo.vo.AlbumScoreVO;
import com.xxxyjade.hiphopghetto.service.AlbumService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "专辑服务")
@Slf4j
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @Operation(summary = "查询专辑")
    @PostMapping("/albums")
    public Result<List<Album>> page(@RequestBody PageQueryDTO pageQueryDTO) {
        return Result.success(albumService.page(pageQueryDTO));

    }

    @Operation(summary = "详情")
    @GetMapping("/album/{id}")
    public Result<AlbumInfoVO> info(@PathVariable("id") Long id) {
        return Result.success(albumService.info(id));
    }

    @Operation(summary = "评分")
    @PostMapping("/album/score")
    public Result<Void> score(@RequestBody AlbumScoreDTO albumScoreDTO) {
        albumService.score(albumScoreDTO);
        return Result.success();
    }

    @Operation(summary = "是否评分过")
    @GetMapping("/album/score/{id}")
    public Result<Integer> hasScore(@PathVariable("id") Long albumId) {
        return Result.success(albumService.hasScore(albumId));
    }

}
