package com.megatron.picserver.service.impl;

import com.megatron.picserver.dao.UserLogDao;
import com.megatron.picserver.pojo.UserLog;
import com.megatron.picserver.service.UserLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @ClassName: UserLogServiceimpl
 * @Description: TODO
 * @Author: zhouhaoyan
 * @date: 2018/8/30 8:11 PM
 * @version: 1.0
 **/
@Service(value = "UserLogService")
public class UserLogServiceimpl implements UserLogService {

    @Autowired
    private UserLogDao userLogDao;

    @Override
    public boolean addLoginLog(String openId, String sessionKey) {
        UserLog userLog = UserLog.builder().openId(openId).sessionKey(sessionKey).createTime(new Date()).build();
        userLogDao.save(userLog);
        return true;
    }
}


