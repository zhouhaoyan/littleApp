package com.megatron.picserver.service.impl;

import com.megatron.picserver.dao.ClassIfyDao;
import com.megatron.picserver.pojo.Classify;
import com.megatron.picserver.service.ClassifyService;
import com.megatron.picserver.utils.base.BaseDao;
import com.megatron.picserver.utils.base.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value ="ClassifyService" )
public class ClassifyServiceImpl extends BaseServiceImpl<Classify,Long> implements ClassifyService {

    @Autowired
    private ClassIfyDao classIfyDao;
    @Override
    public List<Classify> list(Integer status) {
        Map<String,Object> param=new HashMap<>();

        return this.getBaseDao().findList(param);
    }

    @Override
    protected BaseDao<Classify, Long> getBaseDao() {
        return classIfyDao;
    }
}
