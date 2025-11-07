package com.xxxyjade.hiphopghetto.service.impl;

import com.xxxyjade.hiphopghetto.common.pojo.dto.StatsDTO;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Album;
import com.xxxyjade.hiphopghetto.mapper.StatsMapper;
import com.xxxyjade.hiphopghetto.service.StatsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
@Slf4j
public class StatsServiceImpl implements StatsService {

    @Autowired
    private StatsMapper statsMapper;

    /**
     * 查询专辑聚合数据
     */
    public List<Album> selectAlbumStats() {
        List<StatsDTO> albumStatsList = statsMapper.selectStats("album");
        Map<Long, Album> albumMap = new HashMap<>();
        albumStatsList.stream()
                .filter(Objects::nonNull)
                .forEach(statsDTO -> {
                    Album album = albumMap.computeIfAbsent(
                            statsDTO.getResourceId(),
                            k -> Album.builder().id(statsDTO.getResourceId()).build()
                    );
                            int score = statsDTO.getScore();
                            int scoreCount = statsDTO.getScoreCount();
                            int collectCount = statsDTO.getCollectCount();
                            int commentCount = statsDTO.getCommentCount();

                            album.addScoreCount(scoreCount);
                            album.addTotalScore(scoreCount * score);
                            album.addCollectCount(collectCount);
                            album.addCommentCount(commentCount);
                        });
        List<Album> albumList = albumMap.values().stream()
                .filter(Objects::nonNull)
                .peek(album -> {
                    if (album.getScoreCount() != 0) {
                        album.setAvgScore(
                                BigDecimal.valueOf(album.getTotalScore())
                                        .divide(
                                                BigDecimal.valueOf(album.getScoreCount()),
                                                1,
                                                RoundingMode.HALF_UP
                                        )
                        );
                    }
                })
                .toList();
//        albumMapper.updateById(albumList);
        return List.of();
    }
}
