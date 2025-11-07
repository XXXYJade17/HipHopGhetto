package com.xxxyjade.hiphopghetto.service;

import com.xxxyjade.hiphopghetto.common.pojo.dto.PageQueryDTO;
import com.xxxyjade.hiphopghetto.common.pojo.entity.Album;
import com.xxxyjade.hiphopghetto.common.pojo.vo.AlbumInfoVO;
import com.xxxyjade.hiphopghetto.common.pojo.vo.PageVO;

public interface AlbumService {

    /**
     * （条件）分页查询
     */
    PageVO<Album> page(PageQueryDTO pageQueryDTO);

    /**
     * 查询专辑详情
     */
    AlbumInfoVO info(Long id);

}
