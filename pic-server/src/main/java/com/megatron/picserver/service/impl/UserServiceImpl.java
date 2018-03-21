package com.megatron.picserver.service.impl;

import com.megatron.picserver.dao.UserDao;
import com.megatron.picserver.pojo.User;
import com.megatron.picserver.service.UserService;
import com.megatron.picserver.utils.base.BaseDao;
import com.megatron.picserver.utils.base.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "UserService")
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User,Long> implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    protected BaseDao<User, Long> getBaseDao() {
        return userDao;
    }


    @Override
    public boolean singUp(String openId) {
        return false;
    }

    @Override
    public String singIn(String openId) {
        return null;
    }

    @Override
    public void singOut(String oepnId) {

    }
}
