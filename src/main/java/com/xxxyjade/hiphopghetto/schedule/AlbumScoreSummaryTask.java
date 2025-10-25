package com.xxxyjade.hiphopghetto.schedule;

import com.xxxyjade.hiphopghetto.common.pojo.entity.AlbumScoreCount;
import com.xxxyjade.hiphopghetto.common.pojo.entity.AlbumScoreSummary;
import com.xxxyjade.hiphopghetto.mapper.AlbumScoreMapper;
import com.xxxyjade.hiphopghetto.mapper.AlbumScoreSummaryMapper;
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
public class AlbumScoreSummaryTask {

    @Autowired
    private AlbumScoreMapper albumScoreMapper;
    @Autowired
    private AlbumScoreSummaryMapper albumScoreSummaryMapper;

    @Scheduled(fixedRate = 1 * 60 * 1000)
    @Transactional(rollbackFor = Exception.class)
    public void albumScoreSummaryTask() {
        List<AlbumScoreCount> albumScoreCounts = albumScoreMapper.summary();
        log.info("albumScoreCounts:{}", albumScoreCounts);
        Map<Long, AlbumScoreSummary> albumScoreMap = new HashMap<>();
        albumScoreCounts.stream()
                // 过滤不符合的数据
                .filter(albumScoreCount -> albumScoreCount.getCount() >=0 && albumScoreCount.getCount() <= 10)
                // 处理count数据成summary数据
                .forEach(albumScoreCount -> {
                    AlbumScoreSummary albumScoreSummary = albumScoreMap.computeIfAbsent(albumScoreCount.getAlbumId(),
                            k -> AlbumScoreSummary.builder().id(albumScoreCount.getAlbumId()).build());
                    // 设置对应分数的记录数
                    setScoreCount(albumScoreCount, albumScoreSummary);
                });
        // 设置总记录数和综合得分
        albumScoreMap.values().forEach(albumScoreSummary -> {
            int total = getTotal(albumScoreSummary);
            BigDecimal score = getScore(albumScoreSummary, total);
            albumScoreSummary.setTotal(total);
            albumScoreSummary.setScore(score);

        });
        // 插入或更新
        albumScoreSummaryMapper.insertOrUpdate(albumScoreMap.values());
    }

    // 设置对应分数的记录数
    private static void setScoreCount(AlbumScoreCount albumScoreCount, AlbumScoreSummary albumScoreSummary) {
        Integer count = albumScoreCount.getCount();
        switch (albumScoreCount.getScore()){
            case 1 -> albumScoreSummary.setOne(count);
            case 2 -> albumScoreSummary.setTwo(count);
            case 3 -> albumScoreSummary.setThree(count);
            case 4 -> albumScoreSummary.setFour(count);
            case 5 -> albumScoreSummary.setFive(count);
            case 6 -> albumScoreSummary.setSix(count);
            case 7 -> albumScoreSummary.setSeven(count);
            case 8 -> albumScoreSummary.setEight(count);
            case 9 -> albumScoreSummary.setNine(count);
            case 10 -> albumScoreSummary.setTen(count);
        }
    }

    // 计算总记录数
    private static Integer getTotal(AlbumScoreSummary albumScoreSummary) {
        int total = 0;
        total += albumScoreSummary.getOne() == null ? 0 : albumScoreSummary.getOne();
        total += albumScoreSummary.getTwo() == null ? 0 : albumScoreSummary.getTwo();
        total += albumScoreSummary.getThree() == null ? 0 : albumScoreSummary.getThree();
        total += albumScoreSummary.getFour() == null ? 0 : albumScoreSummary.getFour();
        total += albumScoreSummary.getFive() == null ? 0 : albumScoreSummary.getFive();
        total += albumScoreSummary.getSix() == null ? 0 : albumScoreSummary.getSix();
        total += albumScoreSummary.getSeven() == null ? 0 : albumScoreSummary.getSeven();
        total += albumScoreSummary.getEight() == null ? 0 : albumScoreSummary.getEight();
        total += albumScoreSummary.getNine() == null ? 0 : albumScoreSummary.getNine();
        total += albumScoreSummary.getTen() == null ? 0 : albumScoreSummary.getTen();
        return total;
    }

    // 计算综合得分
    private static BigDecimal getScore(AlbumScoreSummary albumScoreSummary, int total) {
        long sum = 0L;
        sum += albumScoreSummary.getOne() == null ? 0L : albumScoreSummary.getOne();
        sum += albumScoreSummary.getTwo() == null ? 0L : albumScoreSummary.getTwo() * 2L;
        sum += albumScoreSummary.getThree() == null ? 0L : albumScoreSummary.getThree() * 3L;
        sum += albumScoreSummary.getFour() == null ? 0L : albumScoreSummary.getFour() * 4L;
        sum += albumScoreSummary.getFive() == null ? 0L : albumScoreSummary.getFive() * 5L;
        sum += albumScoreSummary.getSix() == null ? 0L : albumScoreSummary.getSix() * 6L;
        sum += albumScoreSummary.getSeven() == null ? 0L : albumScoreSummary.getSeven() * 7L;
        sum += albumScoreSummary.getEight() == null ? 0L : albumScoreSummary.getEight() * 8L;
        sum += albumScoreSummary.getNine() == null ? 0L :albumScoreSummary.getNine() * 9L;
        sum += albumScoreSummary.getTen() == null ? 0L : albumScoreSummary.getTen() * 10L;

        return BigDecimal.valueOf(sum).divide(
                BigDecimal.valueOf(total),
                1, // 保留的小数位数
                RoundingMode.HALF_UP // 舍入模式：四舍五入
        );
    }

}
