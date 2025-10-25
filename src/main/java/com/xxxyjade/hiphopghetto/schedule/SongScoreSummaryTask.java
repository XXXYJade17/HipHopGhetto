package com.xxxyjade.hiphopghetto.schedule;

import com.xxxyjade.hiphopghetto.common.pojo.entity.SongScoreCount;
import com.xxxyjade.hiphopghetto.common.pojo.entity.SongScoreSummary;
import com.xxxyjade.hiphopghetto.mapper.SongScoreMapper;
import com.xxxyjade.hiphopghetto.mapper.SongScoreSummaryMapper;
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

@Component
@Slf4j
public class SongScoreSummaryTask {

    @Autowired
    private SongScoreMapper songScoreMapper;
    @Autowired
    private SongScoreSummaryMapper songScoreSummaryMapper;

    @Scheduled(fixedRate = 1 * 60 * 1000)
    @Transactional(rollbackFor = Exception.class)
    public void songScoreSummaryTask() {
        List<SongScoreCount> songScoreCounts = songScoreMapper.summary();
        log.info("songScoreCounts:{}", songScoreCounts);
        Map<Long, SongScoreSummary> songScoreMap = new HashMap<>();
        songScoreCounts.stream()
                // 过滤不符合的数据
                .filter(songScoreCount -> songScoreCount.getCount() >=0 && songScoreCount.getCount() <= 10)
                // 处理count数据成summary数据
                .forEach(songScoreCount -> {
                    SongScoreSummary songScoreSummary = songScoreMap.computeIfAbsent(songScoreCount.getSongId(),
                            k -> SongScoreSummary.builder().id(songScoreCount.getSongId()).build());
                    // 设置对应分数的记录数
                    setScoreCount(songScoreCount, songScoreSummary);
                });
        // 设置总记录数和综合得分
        songScoreMap.values().forEach(songScoreSummary -> {
            int total = getTotal(songScoreSummary);
            BigDecimal score = getScore(songScoreSummary, total);
            songScoreSummary.setTotal(total);
            songScoreSummary.setScore(score);

        });
        // 插入或更新
        songScoreSummaryMapper.insertOrUpdate(songScoreMap.values());
    }

    // 设置对应分数的记录数
    private static void setScoreCount(SongScoreCount songScoreCount, SongScoreSummary songScoreSummary) {
        Integer count = songScoreCount.getCount();
        switch (songScoreCount.getScore()){
            case 1 -> songScoreSummary.setOne(count);
            case 2 -> songScoreSummary.setTwo(count);
            case 3 -> songScoreSummary.setThree(count);
            case 4 -> songScoreSummary.setFour(count);
            case 5 -> songScoreSummary.setFive(count);
            case 6 -> songScoreSummary.setSix(count);
            case 7 -> songScoreSummary.setSeven(count);
            case 8 -> songScoreSummary.setEight(count);
            case 9 -> songScoreSummary.setNine(count);
            case 10 -> songScoreSummary.setTen(count);
        }
    }

    // 计算总记录数
    private static Integer getTotal(SongScoreSummary songScoreSummary) {
        int total = 0;
        total += songScoreSummary.getOne() == null ? 0 : songScoreSummary.getOne();
        total += songScoreSummary.getTwo() == null ? 0 : songScoreSummary.getTwo();
        total += songScoreSummary.getThree() == null ? 0 : songScoreSummary.getThree();
        total += songScoreSummary.getFour() == null ? 0 : songScoreSummary.getFour();
        total += songScoreSummary.getFive() == null ? 0 : songScoreSummary.getFive();
        total += songScoreSummary.getSix() == null ? 0 : songScoreSummary.getSix();
        total += songScoreSummary.getSeven() == null ? 0 : songScoreSummary.getSeven();
        total += songScoreSummary.getEight() == null ? 0 : songScoreSummary.getEight();
        total += songScoreSummary.getNine() == null ? 0 : songScoreSummary.getNine();
        total += songScoreSummary.getTen() == null ? 0 : songScoreSummary.getTen();
        return total;
    }

    // 计算综合得分
    private static BigDecimal getScore(SongScoreSummary songScoreSummary, int total) {
        long sum = 0L;
        sum += songScoreSummary.getOne() == null ? 0L : songScoreSummary.getOne();
        sum += songScoreSummary.getTwo() == null ? 0L : songScoreSummary.getTwo() * 2L;
        sum += songScoreSummary.getThree() == null ? 0L : songScoreSummary.getThree() * 3L;
        sum += songScoreSummary.getFour() == null ? 0L : songScoreSummary.getFour() * 4L;
        sum += songScoreSummary.getFive() == null ? 0L : songScoreSummary.getFive() * 5L;
        sum += songScoreSummary.getSix() == null ? 0L : songScoreSummary.getSix() * 6L;
        sum += songScoreSummary.getSeven() == null ? 0L : songScoreSummary.getSeven() * 7L;
        sum += songScoreSummary.getEight() == null ? 0L : songScoreSummary.getEight() * 8L;
        sum += songScoreSummary.getNine() == null ? 0L :songScoreSummary.getNine() * 9L;
        sum += songScoreSummary.getTen() == null ? 0L : songScoreSummary.getTen() * 10L;

        return BigDecimal.valueOf(sum).divide(
                BigDecimal.valueOf(total),
                1, // 保留的小数位数
                RoundingMode.HALF_UP // 舍入模式：四舍五入
        );
    }

}
