package com.xxxyjade.hiphopghetto.common.pojo.base;

import com.baomidou.mybatisplus.annotation.TableLogic;

import java.time.LocalDateTime;

public class BaseTableData {

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 数据状态
     */
    @TableLogic(value = "0", delval = "1")
    private Integer status;

}
