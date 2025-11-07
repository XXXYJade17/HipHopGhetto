package com.xxxyjade.hiphopghetto.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxyjade.hiphopghetto.common.pojo.dto.CollectCountDTO;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Collect;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CollectMapper extends BaseMapper<Collect> {

    @Select("select resource_id, count(*) as collect_count from collect " +
            "where resource_id in (:ids) " +
            "group by resource_id")
    List<CollectCountDTO> selectCollectCountByIds(@Param("ids") List<Integer> ids);

}
