package com.xxxyjade.hiphopghetto.service.impl;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxxyjade.hiphopghetto.common.enums.SortType;
import com.xxxyjade.hiphopghetto.common.pojo.dto.PageQueryDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.TopicCreateDTO;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Album;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Topic;
import com.xxxyjade.hiphopghetto.common.pojo.vo.PageVO;
import com.xxxyjade.hiphopghetto.mapper.TopicMapper;
import com.xxxyjade.hiphopghetto.service.TopicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Slf4j
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicMapper topicMapper;

    public PageVO<Topic> page(PageQueryDTO pageQueryDTO) {
        SortType sortType = pageQueryDTO.getSortType();
        Page<Topic> page = new Page<>(pageQueryDTO.getPage(), pageQueryDTO.getSize());
        if (sortType != SortType.DEFAULT) {
            page.setOrders(Collections.singletonList(OrderItem.desc(sortType.getType())));
        }
        topicMapper.selectPage(page, null);
        return new PageVO<>(page.getTotal(), page.getRecords());
    }

    /**
     * 创建话题
     */
    public void create(TopicCreateDTO topicCreateDTO) {
        Topic topic = new Topic();
        BeanUtils.copyProperties(topicCreateDTO, topic);
        topicMapper.insert(topic);
    }

}
