package com.megatron.picserver.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.megatron.picserver.jsonBean.UserInfoBean;
import com.megatron.picserver.service.UserService;
import com.megatron.picserver.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "user")
public class UserController {

    @Value("${appId}")
    private String appId;
    @Value("${secret}")
    private String secret;

    @Autowired
    private UserService userService;

    @GetMapping(value = "login")
    public JSONObject login(
            @RequestParam(value = "code") String code
    ) throws Exception {
        String postUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appId + "&secret=" + secret + "&js_code=" + code + "&grant_type=authorization_code";

        String temp = HttpClientUtil.doGet(postUrl, "utf8");
        JSONObject jsonObject = JSON.parseObject(temp);

        userService.singIn(jsonObject.get("openid").toString(), jsonObject.get("session_key").toString());

        return jsonObject;
    }

    @PostMapping(value = "userInfo")
    public boolean updateUserInfo(@RequestBody UserInfoBean userInfoBean) {

        return userService.updateUserInfo(userInfoBean.getOpenId(),userInfoBean.getNickName(),userInfoBean.getAvatarUrl(),userInfoBean.getGender(),userInfoBean.getProvince()
                ,userInfoBean.getCity(),userInfoBean.getCountry());

    }

    public static void main(String[] args) {
        String json = "{\"session_key\":\"\\/NiJSjVOje3zgSorLicjPg==\",\"openid\":\"oRgPM4t-SctnytbxyEUn9-B_4w1w\"}";
        System.out.println(json);
        JSONObject res = JSON.parseObject(json);
        System.out.println(res.toString());
    }

}
