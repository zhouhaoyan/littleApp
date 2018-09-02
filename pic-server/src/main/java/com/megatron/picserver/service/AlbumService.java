package com.megatron.picserver.service;

import com.megatron.picserver.pojo.Album;
import com.megatron.picserver.utils.base.BaseService;
import com.megatron.picserver.utils.base.PageBean;

import java.util.List;

public interface AlbumService extends BaseService<Album,Long> {


    Album addAlbum(String name, String title, String description, String url, Integer isTop, Integer isBanner, Long classIfyId, List<String> lables, Long userId, String studioToken);

    boolean updateAlbum(Long id,String name, String title, String description, String url,
                        Integer isTop, Integer isBanner, Long classIfyId, List<String> lables,Long userId);

    PageBean<Album> page(Integer status, Long classifyId, Integer isBanner, Integer isTop, Integer pageSize, Integer pageNo, String studioToken);

    List<Album> list(Integer status, Long classifyId, Integer isBanner, Integer isTop, String studioToken);




}
