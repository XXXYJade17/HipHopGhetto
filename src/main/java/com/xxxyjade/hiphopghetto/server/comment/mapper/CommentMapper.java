package com.xxxyjade.hiphopghetto.server.comment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxyjade.hiphopghetto.model.entity.Comment;
import org.apache.ibatis.annotations.Update;

public interface CommentMapper extends BaseMapper<Comment> {

    @Update("update comment " +
            "set like_count = like_count + 1 " +
            "where id = #{id}")
    int increaseLikeCount(Long id);

    @Update("update comment " +
            "set like_count = like_count - 1 " +
            "where id = #{id}")
    int decreaseLikeCount(Long id);

    @Update("update comment " +
            "set comment_count = comment_count + 1 " +
            "where id = #{id}")
    int increaseCommentCount(Long id);

    @Update("update comment " +
            "set comment_count = comment_count - 1 " +
            "where id = #{id}")
    int decreaseCommentCount(Long id);

}
