package com.xxxyjade.hiphopghetto.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxxyjade.hiphopghetto.common.pojo.dto.CollectCountDTO;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CommentMapper extends BaseMapper<Comment> {

    Page<Comment> selectCommentPage(Page page, Long commentSectionId);

    @Insert("insert into comment( " +
                "user_id, " +
                "comment_section_id, " +
                "reply_id, " +
                "content, " +
                "username, " +
                "create_time, " +
                "update_time " +
            ") values ( " +
                "#{userId}, " +
                "#{commentSectionId}, " +
                "#{replyId}, " +
                "#{content}, " +
                "(select username from user where id = #{userId}), " +
                "#{createTime}, " +
                "#{updateTime} " +
            ")")
    int insert(Comment comment);

    @Select("select resource_id, count(*) as collect_count from comment " +
            "where resource_id in (:ids) " +
            "group by resource_id")
    List<CollectCountDTO> selectCollectCountByIds(@Param("ids") List<Integer> ids);


}
