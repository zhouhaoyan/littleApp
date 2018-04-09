package com.megatron.picserver.pojo;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.util.Date;
@Data
@Builder
public class Resources {
    private Long id;

    private Integer type;

    private String url;

    private String name;

    private String title;

    private Long classifyId;

    private Integer viewNum;

    private Integer enjoyNum;

    private Integer downloadNum;

    private Long userId;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private String description;

    private String lables;

    private Integer isTop;

    private Long albumId;

    @Tolerate
    public Resources(){}
}