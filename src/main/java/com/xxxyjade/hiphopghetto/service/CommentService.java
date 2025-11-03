package com.xxxyjade.hiphopghetto.service;

import com.xxxyjade.hiphopghetto.common.pojo.dto.CommentDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.PageQueryDTO;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Comment;
import com.xxxyjade.hiphopghetto.common.pojo.vo.PageVO;

public interface CommentService {

    /**
     * （条件）分页查询专辑评论
     */
    PageVO<Comment> commentPage(PageQueryDTO pageQueryDTO);

    /**
     * 创建评论
     */
    void comment(CommentDTO commentDTO);

    /**
     * 删除评论
     */
    void deleteComment(Long id);

}
