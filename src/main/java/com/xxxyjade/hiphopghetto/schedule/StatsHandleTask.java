package com.xxxyjade.hiphopghetto.schedule;

import com.xxxyjade.hiphopghetto.service.AlbumService;
import com.xxxyjade.hiphopghetto.service.SongService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
public class StatsHandleTask {

    @Autowired
    private AlbumService albumService;
    @Autowired
    private SongService songService;

    @Scheduled(fixedRate = 5 * 60 * 1000)   // 五分钟一次
    @Transactional(rollbackFor = Exception.class)
    public void processAvgScore() {
        albumService.processAvgScore();
        songService.processAvgScore();
    }

}
