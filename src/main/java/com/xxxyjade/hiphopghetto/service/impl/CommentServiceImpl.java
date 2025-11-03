package com.xxxyjade.hiphopghetto.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxxyjade.hiphopghetto.common.pojo.dto.CommentDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.PageQueryDTO;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Comment;
import com.xxxyjade.hiphopghetto.common.pojo.vo.PageVO;
import com.xxxyjade.hiphopghetto.mapper.CommentMapper;
import com.xxxyjade.hiphopghetto.service.CommentService;
import com.xxxyjade.hiphopghetto.util.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    /**
     * （条件）分页查询专辑/歌曲评论
     */
    public PageVO<Comment> commentPage(PageQueryDTO pageQueryDTO) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        switch (pageQueryDTO.getSortType()){
            case AVG_SCORE -> wrapper.orderByDesc("avg_score");
            case COLLECT_COUNT -> wrapper.orderByDesc("collect_count");
            case COMMENT_COUNT -> wrapper.orderByDesc("comment_count");
            case NEAREST_TIME -> wrapper.orderByDesc("create_time");
        }
        Page<Comment> page = commentMapper
                .selectPage(new Page<>(pageQueryDTO.getPage(),
                        pageQueryDTO.getSize()), wrapper);
        PageVO<Comment> pageVO = new PageVO<>();
        pageVO.setTotal(page.getTotal());
        pageVO.setData(page.getRecords());
        return pageVO;
    }

    /**
     * 创建评论
     */
    public void comment(CommentDTO commentDTO) {
        Long userId = ThreadUtil.getId();
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
    public void deleteComment(Long id) {
        commentMapper.deleteById(id);
    }

}
