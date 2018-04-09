package com.megatron.picserver.pojo;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.util.Date;
@Data
@Builder
public class Album {
    @Tolerate
    public Album(){

    }
    private Long id;

    private String name;

    private String title;

    private String url;

    private Integer isTop;

    private Integer isBanner;

    private Long userId;

    private Long classifyId;

    private String description;

    private Integer followNum;

    private Integer viewNum;

    private String lables;

    private Long createBy;

    private Date createTime;

    private Date updateTime;

    private Integer status;

}