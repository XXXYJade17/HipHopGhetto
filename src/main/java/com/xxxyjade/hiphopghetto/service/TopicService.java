package com.xxxyjade.hiphopghetto.service;

import com.xxxyjade.hiphopghetto.common.pojo.dto.PageQueryDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.TopicCreateDTO;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Topic;
import com.xxxyjade.hiphopghetto.common.pojo.vo.PageVO;

public interface TopicService {

    PageVO<Topic> page(PageQueryDTO pageQueryDTO);

    void create(TopicCreateDTO topicCreateDTO);

}
