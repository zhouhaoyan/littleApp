package com.megatron.picserver.jsonBean;

import lombok.Data;

@Data
public class UserInfoBean {

    private String openId;
    private String nickName;
    private String avatarUrl;
    private Integer gender;
    private String province;
    private String city;
    private String country;
}
