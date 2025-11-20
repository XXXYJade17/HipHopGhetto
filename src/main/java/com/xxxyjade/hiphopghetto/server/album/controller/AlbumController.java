package com.xxxyjade.hiphopghetto.server.album.controller;

import com.xxxyjade.hiphopghetto.common.constant.ResourceType;
import com.xxxyjade.hiphopghetto.model.enums.SortType;
import com.xxxyjade.hiphopghetto.model.dto.CollectionDTO;
import com.xxxyjade.hiphopghetto.model.dto.PageQueryDTO;
import com.xxxyjade.hiphopghetto.model.dto.RatingDTO;
import com.xxxyjade.hiphopghetto.model.dto.RatingRequestDTO;
import com.xxxyjade.hiphopghetto.model.entity.Album;
import com.xxxyjade.hiphopghetto.model.vo.AlbumDetailVO;
import com.xxxyjade.hiphopghetto.model.vo.PageVO;
import com.xxxyjade.hiphopghetto.common.result.Result;
import com.xxxyjade.hiphopghetto.server.album.service.AlbumService;
import com.xxxyjade.hiphopghetto.server.collection.service.CollectionService;
import com.xxxyjade.hiphopghetto.server.rating.service.RatingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/albums")
@Tag(name = "AlbumController", description = "专辑相关接口")
@AllArgsConstructor
@Slf4j
public class AlbumController {

    private final AlbumService albumService;
    private final CollectionService collectionService;
    private final RatingService ratingService;

    @GetMapping
    @Operation(summary = "（条件）分页查询专辑")
    public Result<PageVO<Album>> page(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size,
            @RequestParam(required = false) SortType sortType) {
        PageQueryDTO pageQueryDTO = PageQueryDTO.builder()
                .page(page)
                .size(size)
                .sortType(sortType)
                .build();
        log.info("分页查询专辑:{}",pageQueryDTO);
        return Result.success(albumService.page(pageQueryDTO));
    }

    @Operation(summary = "查询专辑详情")
    @GetMapping("/{id}")
    public Result<AlbumDetailVO> detail(@PathVariable("id") Long id) {
        log.info("查询专辑详情:{}",id);
        return Result.success(albumService.detail(id));
    }

    @GetMapping("{id}/rating")
    @Operation(summary = "查询专辑评分")
    public Result<Integer> getRating(@PathVariable("id") Long id) {
        log.info("查询专辑评分:{}",id);
        return Result.success(ratingService.select(id));
    }

    @PutMapping("{id}/rating")
    @Operation(summary = "提交/更新专辑评分")
    public Result<Void> rate(
            @PathVariable("id") Long id,
            @RequestBody RatingRequestDTO ratingRequestDTO) {
        RatingDTO ratingDTO = RatingDTO.builder()
                .targetId(id)
                .targetType(ResourceType.ALBUM)
                .score(ratingRequestDTO.getScore())
                .build();
        log.info("提交/更新专辑评分:{}",ratingDTO);
        ratingService.rate(ratingDTO);
        return Result.success();
    }

    @GetMapping("/{id}/collection")
    @Operation(summary = "查询专辑收藏状态")
    public Result<Boolean> getCollection(@PathVariable("id") Long id) {
        log.info("查询专辑收藏状态:{}",id);
        return Result.success(collectionService.select(id));
    }

    @PostMapping("/{id}/collection")
    @Operation(summary = "收藏专辑")
    public Result<Void> collect(@PathVariable("id") Long id) {
        CollectionDTO collectionDTO = CollectionDTO.builder()
                .targetId(id)
                .targetType(ResourceType.ALBUM)
                .build();
        log.info("收藏专辑:{}",collectionDTO);
        collectionService.collect(collectionDTO);
        return Result.success();
    }

    @DeleteMapping("/{id}/collection")
    @Operation(summary = "取消收藏专辑")
    public Result<Void> uncollect(@PathVariable("id") Long id) {
        CollectionDTO collectionDTO = CollectionDTO.builder()
                .targetId(id)
                .targetType(ResourceType.ALBUM)
                .build();
        log.info("取消收藏专辑:{}",collectionDTO);
        collectionService.uncollect(collectionDTO);
        return Result.success();
    }

}
