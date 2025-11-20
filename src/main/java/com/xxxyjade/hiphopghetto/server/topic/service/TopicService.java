package com.xxxyjade.hiphopghetto.server.topic.service;

import com.xxxyjade.hiphopghetto.model.dto.PageQueryDTO;
import com.xxxyjade.hiphopghetto.model.dto.TopicDTO;
import com.xxxyjade.hiphopghetto.model.vo.PageVO;
import com.xxxyjade.hiphopghetto.model.vo.TopicDetailVO;
import com.xxxyjade.hiphopghetto.model.vo.TopicVO;

public interface TopicService {

    /**
     * （条件）分页查询话题
     */
    PageVO<TopicVO> page(PageQueryDTO pageQueryDTO);

    /**
     * 话题详情
     */
    TopicDetailVO detail(Long id);

    /**
     * 创建话题
     */
    void create(TopicDTO topicDTO);

    /**
     * 删除话题
     */
    void delete(Long id);

    /**
     * 更新话题
     */
    void update(TopicDTO topicDTO);

    /**
     * 点赞数递增
     */
    void increaseLikeCount(Long id);

    /**
     * 点赞数递减
     */
    void decreaseLikeCount(Long id);

}
