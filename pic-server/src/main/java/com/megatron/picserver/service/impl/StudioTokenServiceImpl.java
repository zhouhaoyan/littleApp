package com.megatron.picserver.service.impl;

import com.megatron.picserver.dao.StudioTokenDao;
import com.megatron.picserver.pojo.StudioToken;
import com.megatron.picserver.service.StudioTokenService;
import com.megatron.picserver.utils.base.BaseDao;
import com.megatron.picserver.utils.base.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: StudioTokenServiceImpl
 * @Description: 根据 studioToken 获取对应小程序
 * @Author: zhouhaoyan
 * @date: 2018/9/5 2:49 PM
 * @version: 1.0
 **/
@Service(value = "StudioTokenService")
public class StudioTokenServiceImpl extends BaseServiceImpl<StudioToken, Long> implements StudioTokenService {
    @Autowired
    private StudioTokenDao studioTokenDao;

    @Override
    public StudioToken getAppIdParamsByStudioToken(String studioToken) {
        logger.info("根据 studioToken 查询 appInfo，studioToken:{}", studioToken);
        Map<String, Object> paraMap = new HashMap<>();
        paraMap.put("studioToken", studioToken);
        paraMap.put("status", 1);

        List<StudioToken> list = this.getBaseDao().findList(paraMap);
        if (list.size() != 0) {
            logger.info("用户token:{}", list.get(0));
            return list.get(0);

        }
        logger.info("不存在该用户token");
        return null;
    }

    @Override
    protected BaseDao<StudioToken, Long> getBaseDao() {
        return studioTokenDao;
    }
}
