package com.megatron.picserver.service.impl;

import com.megatron.picserver.dao.ResourceLogDao;
import com.megatron.picserver.pojo.ResourceLog;
import com.megatron.picserver.service.ResourceLogService;
import com.megatron.picserver.utils.base.BaseDao;
import com.megatron.picserver.utils.base.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: ResourceLogServiceImpl
 * @Description: TODO
 * @Author: zhouhaoyan
 * @date: 2018/8/31 5:43 PM
 * @version: 1.0
 **/
@Service(value = "ResourceLogService")
public class ResourceLogServiceImpl extends BaseServiceImpl<ResourceLog, Long> implements ResourceLogService {
    @Autowired
    private ResourceLogDao resourceLogDao;

    @Override
    public boolean addRecord(Long rId, String type, String openId, String studioToken) {
        logger.info("rid：{},type:{},openId:{},studioToken:{}", rId, type, openId, studioToken);
        ResourceLog resourceLog = ResourceLog.builder().rId(rId).type(type).openId(openId).studioToken(studioToken).build();
        resourceLogDao.save(resourceLog);

        return true;
    }

    @Override
    protected BaseDao<ResourceLog, Long> getBaseDao() {
        return resourceLogDao;
    }
}
