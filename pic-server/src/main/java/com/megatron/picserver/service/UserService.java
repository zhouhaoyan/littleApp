package com.megatron.picserver.service;

import com.megatron.picserver.pojo.User;
import com.megatron.picserver.utils.base.BaseService;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserService extends BaseService<User,Long> {


    /**
     * 注册
     * @param openId
     * @return
     */
    User singUp (String openId,String sessionKey);

    /**
     * 登录
     * @param  openId
     * @return
     */
    String singIn(String openId,String sessionKey);

    /**
     * 退出
     * @param openId
     */
    void singOut(String openId,String sessionKey);

    boolean updateUserInfo(String openId,String nickName, String avatarUrl, Integer gender, String province, String city, String country);

}
