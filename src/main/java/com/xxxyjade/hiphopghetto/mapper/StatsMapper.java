package com.xxxyjade.hiphopghetto.mapper;

import com.xxxyjade.hiphopghetto.common.pojo.dto.StatsDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StatsMapper {

    @Select("select ${key}.id as resource_id, score.score, score.score_count, " +
            "collect.collect_count, comment.comment_count " +
            "from ${key} " +
            "left join ( " +
                "select resource_id, score, count(*) as score_count from score " +
                "group by resource_id, score " +
            ") as score on score.resource_id = ${key}.id " +
            "left join ( " +
                "select resource_id, count(*) as collect_count from collect " +
                "where collect = 1 " +
                "group by resource_id" +
            ") as collect on collect.resource_id = ${key}.id " +
            "left join ( " +
                "select comment_section_id, count(*) as comment_count from comment " +
                "where status = 1 " +
                "group by comment_section_id " +
            ") as comment on comment.comment_section_id = ${key}.comment_section_id " +
            "where ${key}.staus = 0")
    List<StatsDTO> selectStats(String key);

}
