package com.megatron.picserver.jsonBean;

import lombok.Data;

import java.util.List;

@Data
public class AlbumBean {

    private String name;

    private String title;

    private String description;

    private List<String> imgUrls;

    private String albumUrl;

    private Integer isBanner;

    private Integer isTop;

    private Long classifyId;
}
