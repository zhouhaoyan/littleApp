package com.megatron.picserver.service.impl;

import com.megatron.picserver.dao.UserDao;
import com.megatron.picserver.pojo.User;
import com.megatron.picserver.service.UserLogService;
import com.megatron.picserver.service.UserService;
import com.megatron.picserver.utils.base.BaseDao;
import com.megatron.picserver.utils.base.impl.BaseServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service(value = "UserService")
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User,Long> implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserLogService userLogService;

    @Override
    protected BaseDao<User, Long> getBaseDao() {
        return userDao;
    }


    @Override
    public User singUp(String openId,String sessionKey) {
        User u = User.builder().openId(openId.toString()).sessionKey(sessionKey).createTime(new Date()).build();
        this.getBaseDao().save(u);
        return u;
    }

    @Override
    public String singIn(String openId,String sessionKey) {
        logger.info("openId：{} 登陆系统，添加登陆日志", openId);
        Map<String,Object> param=new HashMap<>();
        param.put("openId",openId);
        List<User> userList=userDao.findList(param);
        if(userList.size()==0){

                    this.singUp(openId,sessionKey);
        }else{
            // User u = User.builder().sessionKey(sessionKey).updateTime(new Date()).build();
            User u = userList.get(0);
            // userDao.updateByOpenId(u);
            //记录登陆日志
            logger.info("用户：{} 登陆系统，添加登陆日志", u.getName());
            userLogService.addLoginLog(openId, sessionKey);
        }

        return "success";
    }

    @Override
    public void singOut(String oepnId,String sessionKey) {

    }

    @Override
    public boolean updateUserInfo(String openId,String nickName, String avatarUrl, Integer gender, String province, String city, String country) {

        List<String> localList= Arrays.asList(city,province,country).stream().filter(v-> !StringUtils.isEmpty(v)).collect(Collectors.toList());
            User user=User.builder().openId(openId).name(nickName).picUrl(avatarUrl).sex(gender).local(localList.toString()).build();
        logger.info("更新用户信息:{}", user.toString());
            userDao.updateByOpenId(user);
            return true;
    }


}
