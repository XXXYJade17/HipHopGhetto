package com.xxxyjade.hiphopghetto.service;

import com.xxxyjade.hiphopghetto.common.pojo.dto.PageQueryDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.PostingCreateDTO;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Posting;
import com.xxxyjade.hiphopghetto.common.pojo.vo.PageVO;

public interface PostingService {

    /**
     * 创建帖子
     * @param postingCreateDTO 创建帖子 DTO
     */
    Void createPosting(PostingCreateDTO postingCreateDTO);

    /**
     * 分页查询
     * @param pageQueryDTO 分页查询DTO
     * @return 分页查询VO
     */
    PageVO<Posting> page(PageQueryDTO pageQueryDTO);

}
