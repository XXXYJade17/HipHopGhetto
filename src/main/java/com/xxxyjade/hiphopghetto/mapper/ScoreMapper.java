package com.xxxyjade.hiphopghetto.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Score;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ScoreMapper extends BaseMapper<Score> {

    @Select("select score from score where user_id = #{userId} and resource_id = #{resourceId}")
    Integer isScored(Long userId, Long resourceId);

    @Select("select score.resource_id, score.score as score, count(score.user_id) as count " +
            "from score left join album on album.id = score.resource_id " +
            "group by score.resource_id, score.score")
    List<ScoreCount> selectAlbumScoreCount();

    @Select("select score.resource_id, score.score as score, count(score.user_id) as count " +
            "from score left join song on song.id = score.resource_id " +
            "group by score.resource_id, score.score")
    List<ScoreCount> selectSongScoreCount();

}
