package com.xxxyjade.hiphopghetto.server.comment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxxyjade.hiphopghetto.model.enums.SortType;
import com.xxxyjade.hiphopghetto.model.dto.CommentDTO;
import com.xxxyjade.hiphopghetto.model.dto.PageQueryDTO;
import com.xxxyjade.hiphopghetto.model.entity.Comment;
import com.xxxyjade.hiphopghetto.model.vo.CommentVO;
import com.xxxyjade.hiphopghetto.model.vo.PageVO;
import com.xxxyjade.hiphopghetto.server.comment.mapper.CommentMapper;
import com.xxxyjade.hiphopghetto.server.comment.service.CommentMessageService;
import com.xxxyjade.hiphopghetto.server.comment.service.CommentService;
import com.xxxyjade.hiphopghetto.util.RedisUtil;
import com.xxxyjade.hiphopghetto.util.ThreadUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;
    private final CommentMessageService commentMessageService;
    private final RedisUtil redisUtil;

    /**
     * （条件）分页查询评论
     */
    @Cacheable(
            value = "commentPage",
            key = "'commentPage::parentId='+ #pageQueryDTO.parentId +'&page=' + #pageQueryDTO.page + '&size=' + #pageQueryDTO.size + '&sort=' + #pageQueryDTO.sortType",
            unless = "#result == null"
    )
    @Transactional(rollbackFor = Exception.class)
    public PageVO<CommentVO> page(PageQueryDTO pageQueryDTO) {
        SortType sortType = pageQueryDTO.getSortType();
        // 创建分页对象
        Page<Comment> page = new Page<>(
                pageQueryDTO.getPage(),
                pageQueryDTO.getSize()
        );
        // 设置排序，默认为倒序
        if (sortType != SortType.DEFAULT) {
            page.setOrders(Collections.singletonList(OrderItem.desc(sortType.getType())));
        }
        QueryWrapper<Comment> wrapper = new QueryWrapper<Comment>()
                .eq("parent_id", pageQueryDTO.getParentId());
        commentMapper.selectPage(page, wrapper);
        List<CommentVO> commentVOList = new ArrayList<>();
        BeanUtils.copyProperties(page.getRecords(), commentVOList);
        return new PageVO<>(page.getTotal(), commentVOList);
    }

    /**
     * 创建评论
     */
    @Transactional(rollbackFor = Exception.class)
    public void create(CommentDTO commentDTO) {
        Long userId = ThreadUtil.getUserId();
        Long parentId = commentDTO.getParentId();

        // 构造实体，并插入
        Comment comment = Comment.builder()
                .userId(userId)
                .parentId(parentId)
                .parentType(commentDTO.getParentType())
                .content(commentDTO.getContent())
                .build();
        int insert = commentMapper.insert(comment);

        if (insert > 0 && parentId != null) {
            // 删除缓存
            String cachePrefix = "commentPage::parentId=" + parentId;
            redisUtil.deleteByPrefix(cachePrefix);

            commentMessageService.sentCommentCountIncreaseMessage(comment);
        }

    }

    /**
     * 删除评论
     */
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id, Long parentId) {
        Comment comment = Comment.builder()
                .id(id)
                .parentId(parentId)
                .build();
        int delete = commentMapper.deleteById(comment);

        if (delete > 0 && parentId != null) {
            // 删除缓存
            String cachePrefix = "commentPage::parentId=" + parentId;
            redisUtil.deleteByPrefix(cachePrefix);

            commentMessageService.sentCommentCountDecreaseMessage(comment);
        }
    }

    /**
     * 点赞数递增
     */
    @Transactional(rollbackFor = Exception.class)
    public void increaseLikeCount(Long id) {
        commentMapper.increaseLikeCount(id);
        // 删除缓存
        String cachePrefix = "commentPage::parentId=" + id;
        redisUtil.deleteByPrefix(cachePrefix);
    }

    /**
     * 点赞数递减
     */
    @Transactional(rollbackFor = Exception.class)
    public void decreaseLikeCount(Long id) {
        commentMapper.decreaseLikeCount(id);
        // 删除缓存
        String cachePrefix = "commentPage::parentId=" + id;
        redisUtil.deleteByPrefix(cachePrefix);
    }

    /**
     * 评论数递增
     */
    @Transactional(rollbackFor = Exception.class)
    public void increaseCommentCount(Long id) {
        commentMapper.increaseCommentCount(id);
        // 删除缓存
        String cachePrefix = "commentPage::parentId=" + id;
        redisUtil.deleteByPrefix(cachePrefix);
    }

    /**
     * 评论数递减
     */
    @Transactional(rollbackFor = Exception.class)
    public void decreaseCommentCount(Long id) {
        commentMapper.decreaseCommentCount(id);
        // 删除缓存
        String cachePrefix = "commentPage::parentId=" + id;
        redisUtil.deleteByPrefix(cachePrefix);
    }

}
