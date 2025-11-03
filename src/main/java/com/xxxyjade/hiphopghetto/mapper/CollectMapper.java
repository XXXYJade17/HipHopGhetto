package com.xxxyjade.hiphopghetto.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Collect;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface CollectMapper extends BaseMapper<Collect> {

    @Select("select collect from collect where user_id = #{userId} and resource_id = #{resourceId}")
    Boolean isCollected(Long userId, Long resourceId);

    @Insert("insert into collect (user_id, resource_id, collect) " +
            "values (#{userId}, #{resourceId}, 1)" +
            "on duplicate key update collect = not collect;")
    void collect(Long userId, Long resourceId);

}
