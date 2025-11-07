package com.xxxyjade.hiphopghetto.service.impl;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxxyjade.hiphopghetto.common.enums.SortType;
import com.xxxyjade.hiphopghetto.common.pojo.dto.CommentDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.CommentPageQueryDTO;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Comment;
import com.xxxyjade.hiphopghetto.common.pojo.vo.PageVO;
import com.xxxyjade.hiphopghetto.mapper.CommentMapper;
import com.xxxyjade.hiphopghetto.service.CommentService;
import com.xxxyjade.hiphopghetto.util.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    /**
     * （条件）分页查询专辑/歌曲评论
     */
    public PageVO<Comment> page(CommentPageQueryDTO commentPageQueryDTO) {
        SortType sortType = commentPageQueryDTO.getSortType();
        Page<Comment> page = new Page<>(commentPageQueryDTO.getPage(), commentPageQueryDTO.getSize());
        if (sortType != SortType.DEFAULT) {
            page.setOrders(Collections.singletonList(OrderItem.desc(sortType.getType())));
        }
        commentMapper.selectPage(page, null);
        return new PageVO<>(page.getTotal(), page.getRecords());
    }

    /**
     * 创建评论
     */
    public void comment(CommentDTO commentDTO) {
        Long userId = ThreadUtil.getUserId();
        Comment comment = Comment.builder()
                .userId(userId)
                .commentSectionId(commentDTO.getCommentSectionId())
                .replyId(commentDTO.getReplyId())
                .content(commentDTO.getContent())
                .build();
        commentMapper.insert(comment);
    }

    /**
     * 删除评论
     */
    public void delete(Long id) {
        commentMapper.deleteById(id);
    }

}
