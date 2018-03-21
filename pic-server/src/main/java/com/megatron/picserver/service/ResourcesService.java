package com.megatron.picserver.service;

import com.megatron.picserver.pojo.Resources;
import com.megatron.picserver.utils.base.BaseService;
import com.megatron.picserver.utils.base.PageBean;

import java.io.IOException;
import java.util.List;

public interface ResourcesService extends BaseService<Resources,Long> {

    /**
     * 获取推荐页列表
     * @param pageSize
     * @param pageNo
     * @return
     */
    PageBean<Resources> getRecommendPage(Integer type,Integer pageSize,Integer pageNo);

    /**
     * 根据分类获取资源列表
     * @param classifyId
     * @param pageSize
     * @param pageNo
     * @return
     */
    PageBean<Resources> getPageByClassify(Integer type,Long classifyId,Integer pageSize,Integer pageNo);

    /**
     * 根据资源类型获取资源列表
     * @param type
     * @param pageSize
     * @param pageNo
     * @return
     */
    PageBean<Resources> getPageByType(Integer type,Integer pageSize,Integer pageNo);

    /**
     * 根据资源ID 获取某资源详细信息
     * @param id
     * @return
     */
    Resources getResourcesById(Long id);

    /**
     *新增资源
     * @param title
     * @param type
     * @param url
     * @param description
     * @param lables
     * @param classify
     * @param userId
     * @param isTop
     * @return
     */
    Resources add(String title, Integer type, String url,String fileName, String description, List<String> lables,Long classifyId,Long userId,Integer isTop) throws IOException;

    /**
     * 更新资源
     * @param id
     * @param title
     * @param type
     * @param url
     * @param description
     * @param lables
     * @param classify
     * @param userId
     * @param isTop
     * @return
     */
    Resources update(Long id,String title, Integer type, String url, String description, List<String> lables,Long classify,Long userId,Integer isTop);




}
