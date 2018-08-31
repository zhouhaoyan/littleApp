package com.megatron.picserver.pojo;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.util.Date;

@Data
@Builder
public class User {
    private Long id;

    private String openId;

    private String name;

    private String nickName;

    private String picUrl;

    private Integer followNum;

    private Integer collectNum;

    private String description;

    private Integer type;

    private Integer sex;

    private String local;

    private Integer level;

    private String sessionKey;

    private String token;

    private Integer status;

    private Date updateTime;

    private Date createTime;

    @Tolerate
    public User(){}


}