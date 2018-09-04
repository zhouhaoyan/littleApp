package com.megatron.picserver.pojo;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.util.Date;

@Data
@Builder
public class ResourceLog {
    private Long id;

    private Long rId;

    private String type;

    private Date createTime;

    private String openId;

    private String studioToken;

    @Tolerate
    public ResourceLog() {

    }


}