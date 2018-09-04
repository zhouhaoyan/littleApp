package com.megatron.picserver.service;

import com.megatron.picserver.pojo.ResourceLog;
import com.megatron.picserver.utils.base.BaseService;

/**
 * @ClassName: ResourceLogService
 * @Description: TODO
 * @Author: zhouhaoyan
 * @date: 2018/8/31 5:42 PM
 * @version: 1.0
 **/
public interface ResourceLogService extends BaseService<ResourceLog, Long> {

    boolean addRecord(Long rId, String type, String openId, String studioToken);
}
