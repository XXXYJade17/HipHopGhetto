package com.xxxyjade.hiphopghetto.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxxyjade.hiphopghetto.common.enums.SortType;
import com.xxxyjade.hiphopghetto.common.pojo.dto.CommentDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.CommentPageQueryDTO;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Comment;
import com.xxxyjade.hiphopghetto.common.pojo.vo.PageVO;
import com.xxxyjade.hiphopghetto.mapper.CommentMapper;
import com.xxxyjade.hiphopghetto.service.CommentService;
import com.xxxyjade.hiphopghetto.util.KeyGenerator;
import com.xxxyjade.hiphopghetto.util.RedisUtil;
import com.xxxyjade.hiphopghetto.util.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private KeyGenerator keyGenerator;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * （条件）分页查询专辑/歌曲评论
     */
    @Cacheable(
            value = "commentPage", // 与工具类中生成的键前缀一致
            key = "#keyGenerator.generateCommentPageKey(" +
                    "#commentPageQueryDTO.commentSectionId, " +
                    "#commentPageQueryDTO.page, " +
                    "#commentPageQueryDTO.size, " +
                    "#commentPageQueryDTO.sortType" +
                    ")",
            unless = "#result == null"
    )
    public PageVO<Comment> page(CommentPageQueryDTO commentPageQueryDTO) {
        SortType sortType = commentPageQueryDTO.getSortType();
        Page<Comment> page = new Page<>(commentPageQueryDTO.getPage(), commentPageQueryDTO.getSize());
        if (sortType != SortType.DEFAULT) {
            page.setOrders(Collections.singletonList(OrderItem.desc(sortType.getType())));
        }
        QueryWrapper<Comment> wrapper = new QueryWrapper<Comment>()
                .eq("comment_section_id", commentPageQueryDTO.getCommentSectionId());
        commentMapper.selectPage(page, wrapper);
        return new PageVO<>(page.getTotal(), page.getRecords());
    }

    /**
     * 创建评论
     */
    @Transactional(rollbackFor = Exception.class)
    public void comment(CommentDTO commentDTO) {
        Long userId = ThreadUtil.getUserId();
        Long sectionId = commentDTO.getCommentSectionId();
        Comment comment = Comment.builder()
                .userId(userId)
                .commentSectionId(sectionId)
                .replyId(commentDTO.getReplyId())
                .content(commentDTO.getContent())
                .build();
        commentMapper.insert(comment);

        String cachePrefix = keyGenerator.getCommentSectionCachePrefix(sectionId);
        redisUtil.deleteByPrefix(cachePrefix);
    }

    /**
     * 删除评论
     */
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        // 先查询评论所属的评论区ID
        Comment comment = commentMapper.selectById(id);
        if (comment == null) {
            throw new RuntimeException("评论不存在");
        }
        Long sectionId = comment.getCommentSectionId();

        // 删除评论
        commentMapper.deleteById(id);

        // 删除该评论区的所有缓存
        String cachePrefix = keyGenerator.getCommentSectionCachePrefix(sectionId);
        redisUtil.deleteByPrefix(cachePrefix);
    }

}
