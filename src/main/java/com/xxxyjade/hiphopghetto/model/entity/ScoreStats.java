package com.xxxyjade.hiphopghetto.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScoreStats {

    private Integer scoreCount = 0;

    private Integer totalScore = 0;

    public BigDecimal getAvgScore() {
        return BigDecimal.valueOf(totalScore)
                .divide(
                        BigDecimal.valueOf(scoreCount),
                        1,
                        RoundingMode.HALF_UP
                );
    }

}
