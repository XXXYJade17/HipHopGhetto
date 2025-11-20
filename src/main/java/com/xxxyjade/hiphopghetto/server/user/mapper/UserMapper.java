package com.xxxyjade.hiphopghetto.server.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxyjade.hiphopghetto.model.entity.User;
import org.apache.ibatis.annotations.Select;

public interface UserMapper extends BaseMapper<User> {

    @Select("select exists(select 1 from user where username = #{username})")
    Boolean existByUsername(String username);

}
