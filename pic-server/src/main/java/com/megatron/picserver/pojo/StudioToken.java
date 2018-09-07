package com.megatron.picserver.pojo;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.util.Date;

@Builder
@Data
public class StudioToken {
    private Long id;

    private String name;

    private String phone;

    private String studioToken;

    private String appid;

    private String secret;

    private Date createTime;

    private Integer status;

    @Tolerate
    public StudioToken() {

    }
}