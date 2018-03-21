package com.megatron.picserver.pojo;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.util.Date;
@Data
@Builder
public class Classify {
    private Long id;

    private String url;

    private String name;

    private Date createTime;

    private Date updateTime;

    private Integer status;

    @Tolerate
    public Classify(){}

}