package com.xxxyjade.hiphopghetto.controller;

import com.xxxyjade.hiphopghetto.common.pojo.dto.PageQueryDTO;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Album;
import com.xxxyjade.hiphopghetto.common.pojo.vo.AlbumInfoVO;
import com.xxxyjade.hiphopghetto.common.pojo.vo.PageVO;
import com.xxxyjade.hiphopghetto.common.result.Result;
import com.xxxyjade.hiphopghetto.service.AlbumService;
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

}
