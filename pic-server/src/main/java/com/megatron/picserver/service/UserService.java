package com.megatron.picserver.service;

import com.megatron.picserver.pojo.User;
import com.megatron.picserver.utils.base.BaseService;

public interface UserService extends BaseService<User,Long> {


    /**
     * 注册
     * @param openId
     * @return
     */
    boolean singUp (String openId);

    /**
     * 登录
     * @param  openId
     * @return
     */
    String singIn(String openId);

    /**
     * 退出
     * @param oepnId
     */
    void singOut(String oepnId);

}
