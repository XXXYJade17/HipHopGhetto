package com.xxxyjade.hiphopghetto.service.schedule;

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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class StatsHandleTask {

    @Autowired
    private AlbumMapper albumMapper;
    @Autowired
    private StatsMapper scoreMapper;
    @Autowired
    private SongMapper songMapper;

    @Scheduled(fixedRate = 5 * 60 * 1000)   // 五分钟一次
    @Transactional(rollbackFor = Exception.class)
    public void processAlbumStats() {
        log.info("开始处理专辑数据");
        List<StatsDTO> albumStatsList = scoreMapper.selectStats("album");
        Map<Long, Album> albumMap = new HashMap<>();
        albumStatsList.forEach(albumStats -> {
            Album album = albumMap.computeIfAbsent(albumStats.getResourceId(), k -> Album.builder().id(albumStats.getResourceId()).build());
            album.setTotalScore(album.getTotalScore() + (long) albumStats.getScoreCount() * albumStats.getScore());
            album.setScoreCount(album.getScoreCount() + albumStats.getScoreCount());
            album.setCollectCount(album.getCollectCount() + albumStats.getCollectCount());
            album.setCommentCount(album.getCommentCount() + albumStats.getCommentCount());
        });
        albumMapper.updateById(albumMap.values());
    }

    @Scheduled(fixedRate = 5 * 60 * 1000)   // 五分钟一次
    @Transactional(rollbackFor = Exception.class)
    public void processSongStats() {
        log.info("开始处理歌曲数据");
        List<StatsDTO> songStatsList = scoreMapper.selectStats("song");
        Map<Long, Song> songMap = new HashMap<>();
        songStatsList.forEach(songStats -> {
            Song song = songMap.computeIfAbsent(songStats.getResourceId(), k -> Song.builder().id(songStats.getResourceId()).build());
            song.setTotalScore(song.getTotalScore() + (long) songStats.getScoreCount() * songStats.getScore());
            song.setScoreCount(song.getScoreCount() + songStats.getScoreCount());
            song.setCollectCount(song.getCollectCount() + songStats.getCollectCount());
            song.setCommentCount(song.getCommentCount() + songStats.getCommentCount());
        });
        songMapper.updateById(songMap.values());
    }

}
