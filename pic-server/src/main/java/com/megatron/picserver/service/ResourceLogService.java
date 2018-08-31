package com.megatron.picserver.service;

/**
 * @ClassName: ResourceLogService
 * @Description: TODO
 * @Author: zhouhaoyan
 * @date: 2018/8/31 5:42 PM
 * @version: 1.0
 **/
public interface ResourceLogService {

    boolean addRecord(Long rId, String type, String openId);
}
