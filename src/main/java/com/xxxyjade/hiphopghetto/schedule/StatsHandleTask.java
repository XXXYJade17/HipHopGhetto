package com.xxxyjade.hiphopghetto.schedule;

import com.xxxyjade.hiphopghetto.common.pojo.entity.BaseStats;
import com.xxxyjade.hiphopghetto.common.pojo.entity.ScoreCount;
import com.xxxyjade.hiphopghetto.common.pojo.entity.AlbumStats;
import com.xxxyjade.hiphopghetto.common.pojo.entity.SongStats;
import com.xxxyjade.hiphopghetto.mapper.AlbumScoreMapper;
import com.xxxyjade.hiphopghetto.mapper.AlbumStatsMapper;
import com.xxxyjade.hiphopghetto.mapper.SongScoreMapper;
import com.xxxyjade.hiphopghetto.mapper.SongStatsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

@Component
@Slf4j
public class StatsHandleTask {

    @Autowired
    private AlbumScoreMapper albumScoreMapper;
    @Autowired
    private AlbumStatsMapper albumStatsMapper;
    @Autowired
    private SongScoreMapper songScoreMapper;
    @Autowired
    private SongStatsMapper songStatsMapper;

    @Scheduled(fixedRate = 5 * 60 * 1000)   // 五分钟一次
    @Transactional(rollbackFor = Exception.class)
    public void processAlbumStats() {
        log.info("开始处理专辑聚合数据");
        List<ScoreCount> albumScoreCounts = albumScoreMapper.getScoreCount();
        // 调用通用方法处理
        processStats(
                albumScoreCounts,
                albumId -> AlbumStats.builder().albumId(albumId).build(), // 创建AlbumStats
                albumStatsMapper::insertOrUpdate // 保存专辑统计
        );
    }

    @Scheduled(fixedRate = 5 * 60 * 1000)   // 五分钟一次
    @Transactional(rollbackFor = Exception.class)
    public void processSongStats() {
        log.info("开始处理歌曲聚合数据");
        List<ScoreCount> songScoreCounts = songScoreMapper.getScoreCount();
        // 调用通用方法处理
        processStats(
                songScoreCounts,
                songId -> SongStats.builder().songId(songId).build(), // 创建AlbumStats
                songStatsMapper::insertOrUpdate // 保存专辑统计
        );
    }

    private <T extends BaseStats> void processStats(List<ScoreCount> scoreCounts,
                                                    Function<Long, T> statsCreator,
                                                    Consumer<Collection<T>> saveFunction) {
        Map<Long, T> statsMap = new HashMap<>();

        scoreCounts.stream()
                // 过滤不符合条件的数据
                .filter(scoreCount -> scoreCount.getCount() >= 0 && scoreCount.getCount() <= 10)
                .forEach(scoreCount -> {
                    // 根据ID获取或创建统计对象
                    T stats = statsMap.computeIfAbsent(scoreCount.getId(), statsCreator);
                    // 设置对应分数的数量
                    stats.setScoreCount(scoreCount);
                });

        // 计算总评分数和平均评分
        statsMap.values().forEach(stats -> {
            stats.calculateTotalScoreCount();
            stats.calculateAvgScore();
        });

        // 保存统计结果
        saveFunction.accept(statsMap.values());
    }

}
