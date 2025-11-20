package com.xxxyjade.hiphopghetto.server.topic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxyjade.hiphopghetto.model.entity.Topic;
import org.apache.ibatis.annotations.Update;

public interface TopicMapper extends BaseMapper<Topic> {

    @Update("update topic " +
            "set like_count = like_count + 1 " +
            "where id = #{id}")
    int increaseLikeCount(Long id);

    @Update("update topic " +
            "set like_count = like_count - 1 " +
            "where id = #{id}")
    int decreaseLikeCount(Long id);

}
