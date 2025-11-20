package com.xxxyjade.hiphopghetto.server.topic.service.impl;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxxyjade.hiphopghetto.model.enums.SortType;
import com.xxxyjade.hiphopghetto.model.dto.PageQueryDTO;
import com.xxxyjade.hiphopghetto.model.dto.TopicDTO;
import com.xxxyjade.hiphopghetto.model.entity.Topic;
import com.xxxyjade.hiphopghetto.model.vo.PageVO;
import com.xxxyjade.hiphopghetto.model.vo.TopicDetailVO;
import com.xxxyjade.hiphopghetto.model.vo.TopicVO;
import com.xxxyjade.hiphopghetto.server.topic.mapper.TopicMapper;
import com.xxxyjade.hiphopghetto.server.like.service.LikeService;
import com.xxxyjade.hiphopghetto.server.topic.service.TopicService;
import com.xxxyjade.hiphopghetto.util.ThreadUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class TopicServiceImpl implements TopicService {

    private final LikeService likeService;
    private final TopicMapper topicMapper;

    /**
     * （条件）分页查询话题
     */
    @Cacheable(
            value = "topicPage",
            key = "'topicPage::page=' + #pageQueryDTO.page + '&size=' + #pageQueryDTO.size + '&sort=' + #pageQueryDTO.sortType",
            unless = "#result == null"
    )
    @Transactional(rollbackFor = Exception.class)
    public PageVO<TopicVO> page(PageQueryDTO pageQueryDTO) {
        SortType sortType = pageQueryDTO.getSortType();
        // 创建分页对象
        Page<Topic> page = new Page<>(
                pageQueryDTO.getPage(),
                pageQueryDTO.getSize()
        );
        // 设置排序，默认为倒序
        if (sortType != SortType.DEFAULT) {
            page.setOrders(Collections.singletonList(OrderItem.desc(sortType.getType())));
        }
        topicMapper.selectPage(page, null);
        List<TopicVO> topicVOList = new ArrayList<>();
        BeanUtils.copyProperties(page.getRecords(), topicVOList);
        return new PageVO<>(page.getTotal(), topicVOList);
    }

    /**
     * 话题详情
     */
    @Cacheable(
            value = "topicDetail",
            key = "'topicDetail::id='+ #id",
            unless = "#result == null"
    )
    @Transactional(rollbackFor = Exception.class)
    public TopicDetailVO detail(Long id) {
        Topic topic = topicMapper.selectById(id);
        Boolean isLiked = likeService.select(id);
        return TopicDetailVO.builder()
                .content(topic.getContent())
                .viewCount(topic.getViewCount())
                .commentCount(topic.getCommentCount())
                .likeCount(topic.getLikeCount())
                .isLiked(isLiked)
                .createTime(topic.getCreateTime())
                .build();
    }

    /**
     * 创建话题
     */
    @Transactional(rollbackFor = Exception.class)
    public void create(TopicDTO topicDTO) {
        Topic topic = Topic.builder()
                .userId(ThreadUtil.getUserId())
                .title(topicDTO.getTitle())
                .content(topicDTO.getContent())
                .coverUrl(topicDTO.getCoverUrl())
                .build();
        topicMapper.insert(topic);
    }

    /**
     * 删除话题
     */
    @Caching(evict = {
            @CacheEvict(
                    value = "topicDetail",
                    key = "'topicDetail::id=' + #id"
            ),
            @CacheEvict(
                    value = "topicPage",
                    allEntries = true
            )
    })
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        topicMapper.deleteById(id);
    }

    /**
     * 更新话题
     */
    @CacheEvict(
            value = "topicDetail",
            key = "'topicDetail::id=' + #topicDTO.id"
    )
    @Transactional(rollbackFor = Exception.class)
    public void update(TopicDTO topicDTO) {
        Topic topic = Topic.builder()
                .id(topicDTO.getId())
                .title(topicDTO.getTitle())
                .content(topicDTO.getContent())
                .coverUrl(topicDTO.getCoverUrl())
                .build();
        topicMapper.updateById(topic);
    }

    /**
     * 点赞数递增
     */
    @CacheEvict(
            value = "topicDetail",
            key = "'topicDetail::id=' + #id"
    )
    @Transactional(rollbackFor = Exception.class)
    public void increaseLikeCount(Long id) {
        topicMapper.increaseLikeCount(id);
    }

    /**
     * 点赞数递减
     */
    @CacheEvict(
            value = "topicDetail",
            key = "'topicDetail::id=' + #id"
    )
    @Transactional(rollbackFor = Exception.class)
    public void decreaseLikeCount(Long id) {
        topicMapper.decreaseLikeCount(id);
    }

}
