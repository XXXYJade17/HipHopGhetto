package com.xxxyjade.hiphopghetto.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxxyjade.hiphopghetto.common.pojo.dto.PageQueryDTO;
import com.xxxyjade.hiphopghetto.common.pojo.dto.PostingCreateDTO;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Posting;
import com.xxxyjade.hiphopghetto.common.pojo.entity.PostingImage;
import com.xxxyjade.hiphopghetto.common.pojo.vo.PageVO;
import com.xxxyjade.hiphopghetto.mapper.PostingImageMapper;
import com.xxxyjade.hiphopghetto.mapper.PostingMapper;
import com.xxxyjade.hiphopghetto.service.PostingService;
import com.xxxyjade.hiphopghetto.util.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PostingServiceImpl implements PostingService {

    @Autowired
    private PostingMapper postingMapper;
    @Autowired
    private PostingImageMapper postingImageMapper;

    /**
     * 创建帖子
     * @param postingCreateDTO 创建帖子 DTO
     */
    public Void createPosting(PostingCreateDTO postingCreateDTO) {
        // 插入帖子
        Posting posting = Posting.builder()
                .userId(ThreadUtil.getId())
                .title(postingCreateDTO.getTitle())
                .content(postingCreateDTO.getContent())
                .build();
        postingMapper.insert(posting);

        // 批量插入图片
        postingCreateDTO.getImages().forEach((String url) -> {
            postingImageMapper.insert(PostingImage.builder()
                    .postingId(posting.getId())
                    .url(url)
                    .build()
            );
        });
        return null;
    }

    /**
     * 帖子分页查询
     * @param pageQueryDTO 分页查询DTO
     * @return 分页查询VO
     */
    public PageVO<Posting> page(PageQueryDTO pageQueryDTO) {
        Page<Posting> page = new Page<>(pageQueryDTO.getPage(), pageQueryDTO.getSize());
        Page<Posting> res = postingMapper.selectPage(page, null);
        PageVO<Posting> pageVO = new PageVO<>();
        pageVO.setList(res.getRecords());
        return pageVO;
    }

}
