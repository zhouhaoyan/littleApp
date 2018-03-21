package com.megatron.picserver.service;

import com.megatron.picserver.pojo.Classify;
import com.megatron.picserver.utils.base.BaseService;

import java.util.List;

public interface ClassifyService extends BaseService<Classify,Long> {


    /**
     * 获取分类列表
     * @param status
     * @return
     */
    List<Classify> list(Integer status);
}
