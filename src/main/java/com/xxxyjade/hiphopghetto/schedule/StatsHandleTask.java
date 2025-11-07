package com.xxxyjade.hiphopghetto.schedule;

import com.xxxyjade.hiphopghetto.common.pojo.dto.StatsDTO;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Album;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Song;
import com.xxxyjade.hiphopghetto.mapper.AlbumMapper;
import com.xxxyjade.hiphopghetto.mapper.SongMapper;
import com.xxxyjade.hiphopghetto.mapper.StatsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
@Slf4j
public class StatsHandleTask {

    @Autowired
    private AlbumMapper albumMapper;
    @Autowired
    private SongMapper songMapper;
    @Autowired
    private StatsMapper statsMapper;

//    @Scheduled(fixedRate = 5 * 60 * 1000)   // 五分钟一次
//    @Transactional(rollbackFor = Exception.class)
//    public void processAlbumStats() {
//        log.info("开始处理专辑数据");
//        List<StatsDTO> albumStatsList = statsMapper.selectStats("album");
//        Map<Long, Album> albumMap = new HashMap<>();
//        albumStatsList.forEach(albumStats -> {
//            Album album = albumMap.computeIfAbsent(albumStats.getResourceId(), k -> Album.builder().id(albumStats.getResourceId()).build());
//
//            int score = albumStats.getScore();
//            int scoreCount = albumStats.getScoreCount();
//            int collectCount = albumStats.getCollectCount();
//            int commentCount = albumStats.getCommentCount();
//
//            if (scoreCount != 0 && score != -1) {
//                album.addScoreCount(scoreCount);
//                album.addTotalScore(scoreCount * score);
//            }
//            if (collectCount != 0) {
//                album.addCollectCount(collectCount);
//            }
//            if (commentCount != 0) {
//                album.addCommentCount(commentCount);
//            }
//
//        });
//        List<Album> albumList = albumMap.values().stream()
//                .filter(Objects::nonNull)
//                .peek(album -> {
//                    if (album.getScoreCount() != 0) {
//                        album.setAvgScore(
//                                BigDecimal.valueOf(album.getTotalScore())
//                                        .divide(
//                                                BigDecimal.valueOf(album.getScoreCount()),
//                                                1,
//                                                RoundingMode.HALF_UP
//                                        )
//                        );
//                    }
//                })
//                .toList();
//        albumMapper.updateById(albumList);
//    }
//
//    @Scheduled(fixedRate = 5 * 60 * 1000)   // 五分钟一次
//    @Transactional(rollbackFor = Exception.class)
//    public void processSongStats() {
//        log.info("开始处理歌曲数据");
//        List<StatsDTO> songStatsList = statsMapper.selectStats("song");
//        log.debug("查询到歌曲统计数据: {}", songStatsList);
//        Map<Long, Song> songMap = new HashMap<>();
//        songStatsList.forEach(songStats -> {
//            Song song = songMap.computeIfAbsent(songStats.getResourceId(), k -> Song.builder().id(songStats.getResourceId()).build());
//
//            int score = songStats.getScore();
//            int scoreCount = songStats.getScoreCount();
//            int collectCount = songStats.getCollectCount();
//            int commentCount = songStats.getCommentCount();
//
//            if (scoreCount != 0 && score != -1) {
//                song.addScoreCount(scoreCount);
//                song.addTotalScore(scoreCount * score);
//            }
//            if (collectCount != 0) {
//                song.addCollectCount(collectCount);
//            }
//            if (commentCount != 0) {
//                song.addCommentCount(commentCount);
//            }
//
//        });
//        List<Song> songList = songMap.values().stream()
//                .filter(Objects::nonNull)
//                .peek(song -> {
//                    if (song.getScoreCount() != 0) {
//                        song.setAvgScore(
//                                BigDecimal.valueOf(song.getTotalScore())
//                                        .divide(
//                                                BigDecimal.valueOf(song.getScoreCount()),
//                                                1,
//                                                RoundingMode.HALF_UP
//                                        )
//                        );
//                    }
//                })
//                .toList();
//        songMapper.updateById(songList);
//    }

}
