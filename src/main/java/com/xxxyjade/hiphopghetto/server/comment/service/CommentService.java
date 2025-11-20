package com.xxxyjade.hiphopghetto.server.comment.service;

import com.xxxyjade.hiphopghetto.model.dto.CommentDTO;
import com.xxxyjade.hiphopghetto.model.dto.PageQueryDTO;
import com.xxxyjade.hiphopghetto.model.vo.CommentVO;
import com.xxxyjade.hiphopghetto.model.vo.PageVO;

public interface CommentService {

    /**
     * （条件）分页查询评论
     */
    PageVO<CommentVO> page(PageQueryDTO pageQueryDTO);

    /**
     * 创建评论
     */
    void create(CommentDTO commentDTO);

    /**
     * 删除评论
     */
    void delete(Long id, Long parentId);

    /**
     * 点赞数数递增
     */
    void increaseLikeCount(Long id);

    /**
     * 点赞数数递减
     */
    void decreaseLikeCount(Long id);

    /**
     * 点赞数递增
     */
    void increaseCommentCount(Long id);

    /**
     * 点赞数递减
     */
    void decreaseCommentCount(Long id);

}
