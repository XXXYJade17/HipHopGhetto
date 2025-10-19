package com.xxxyjade.hiphopghetto.common.pojo.base;

import com.baomidou.mybatisplus.annotation.TableLogic;

public class BaseTableData {

    /**
     * 数据状态
     * 0 - 正常
     * 1 - 失效
     */
    @TableLogic(value = "0", delval = "1")
    private Integer status = 0;

}
