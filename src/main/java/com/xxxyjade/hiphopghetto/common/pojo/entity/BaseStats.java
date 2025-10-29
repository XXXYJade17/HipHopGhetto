package com.xxxyjade.hiphopghetto.common.pojo.entity;

import lombok.*;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

public class BaseStats {

    /**
     * 平均评分
     */
    private BigDecimal avgScore;

    /**
     * 评分数
     */
    private Integer scoreCount = 0;

    /**
     * 收藏数
     */
    private Integer collectCount = 0;

    /**
     * 评论数
     */
    private Integer commentCount = 0;

    /**
     * 评分情况
     */
    Integer one = 0;
    Integer two = 0;
    Integer three = 0;
    Integer four = 0;
    Integer five = 0;
    Integer six = 0;
    Integer seven = 0;
    Integer eight = 0;
    Integer nine = 0;
    Integer ten = 0;

    @SneakyThrows
    public void setScoreCount(ScoreCount scoreCount) {
        // 如果对象为空
        if (scoreCount == null) {
            return;
        }
        String score = scoreCount.getScore();
        Integer count = scoreCount.getCount();
        // 获取BaseStats的Class对象
        Class<?> baseStatsClass = BaseStats.class;
        // 创建setter方法
        Method setterMethod = baseStatsClass.getMethod("set" + StringUtils.capitalize(score), Integer.class);
        // 调用setter方法赋值
        setterMethod.invoke(this, count);
    }

    public void calculateTotalScoreCount() {
        // 用数组简化累加逻辑（对应1-10分的数量）
        Integer[] scoreCounts = {one, two, three, four, five, six, seven, eight, nine, ten};
        this.scoreCount = Arrays.stream(scoreCounts).mapToInt(Integer::intValue).sum();
    }

    public void calculateAvgScore() {
        // 计算总分（分数×数量）
        Integer[] scoreCounts = {one, two, three, four, five, six, seven, eight, nine, ten};
        int totalScore = 0;
        for (int i = 0; i < scoreCounts.length; i++) {
            totalScore += scoreCounts[i] * (i + 1); // i+1对应1-10分
        }
        this.avgScore = BigDecimal.valueOf(totalScore)
                .divide(BigDecimal.valueOf(scoreCount), 1, RoundingMode.HALF_UP);
    }

}
