package com.megatron.picserver.dao;

import com.megatron.picserver.pojo.UserLog;
import com.megatron.picserver.utils.base.BaseDao;

public interface UserLogDao extends BaseDao<UserLog, Long> {

    void updateByOpenId(UserLog user);
}
