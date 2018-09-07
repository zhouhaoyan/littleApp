package com.megatron.picserver.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.megatron.picserver.jsonBean.UserInfoBean;
import com.megatron.picserver.pojo.StudioToken;
import com.megatron.picserver.service.StudioTokenService;
import com.megatron.picserver.service.UserService;
import com.megatron.picserver.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private StudioTokenService studioTokenService;

    @GetMapping(value = "login")
    public JSONObject login(
            @RequestParam(value = "code") String code,
            @RequestParam(value = "studioToken") String studioToken,
            @RequestParam(value = "version", required = false) String version
    ) throws Exception {

        // 通过 studio 表配置 studioToken 与 appId  secret 关联关系，动态获取 appId secret
        StudioToken token = studioTokenService.getAppIdParamsByStudioToken(studioToken);

        String postUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=" + token.getAppid() + "&secret=" + token.getSecret() + "&js_code=" + code + "&grant_type=authorization_code";

        String temp = HttpClientUtil.doGet(postUrl, "utf8");
        JSONObject jsonObject = JSON.parseObject(temp);

        userService.singIn(jsonObject.get("openid").toString(), jsonObject.get("session_key").toString(), version);

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
