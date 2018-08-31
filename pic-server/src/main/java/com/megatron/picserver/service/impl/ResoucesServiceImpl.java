package com.megatron.picserver.service.impl;

import com.github.pagehelper.PageHelper;
import com.megatron.picserver.dao.ResourcesDao;
import com.megatron.picserver.pojo.Resources;
import com.megatron.picserver.service.ResourcesService;
import com.megatron.picserver.utils.Const;
import com.megatron.picserver.utils.UploadUtil;
import com.megatron.picserver.utils.base.BaseDao;
import com.megatron.picserver.utils.base.PageBean;
import com.megatron.picserver.utils.base.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = "ResourcesService")
@Transactional
public class ResoucesServiceImpl extends BaseServiceImpl<Resources,Long> implements ResourcesService{

    @Autowired
    private ResourcesDao resourcesDao;

    @Autowired
    private UploadUtil uploadUtil;

    @Value("${file.server.path}")
    private String fileServerPath;

    @Override
    public PageBean<Resources> getRecommendPage(Integer type,Integer pageSize, Integer pageNo) {
        return this.page(Const.STATUS_NORMAL,type,Const.IS_TOP_ON,null,pageSize,pageNo);
    }

    @Override
    public PageBean<Resources> getPageByClassify(Integer type,Long classifyId, Integer pageSize, Integer pageNo) {
        return this.page(Const.STATUS_NORMAL,type,null,classifyId,pageSize,pageNo);
    }

    @Override
    public PageBean<Resources> getPageByType(Integer type, Integer pageSize, Integer pageNo) {
        return this.page(Const.STATUS_NORMAL,type,null,null,pageSize,pageNo);
    }

    @Override
    public Resources getResourcesById(Long id) {
        return this.getBaseDao().getById(id);
    }

    @Override
    public Resources add(Long albumId,String title, Integer type, String url,String fileName, String description, List<String> lables, Long classify, Long userId, Integer isTop) {
        Resources resources = Resources.builder().title(title).type(type).url(url).createTime(new Date()).updateTime(new Date())
                .description(description).
                        lables(null).classifyId(classify).userId(userId).isTop(isTop).albumId(albumId).status(Const.STATUS_NORMAL).build();

        this.getBaseDao().save(resources);

//        try {
//            uploadUtil.upload(url,fileName);
//        } catch (IOException e) {
//            e.printStackTrace();
//            logger.error("上传图片至七牛云失败:本地路径:"+url+"图片名称:"+fileName);
//            throw e;
//        }
        return resources;
    }

    @Override
    public Resources update(Long id, String title, Integer type, String url, String description, List<String> lables, Long classify, Long userId, Integer isTop) {

        Resources resources=Resources.builder().title(title).type(type).url(this.buildFileUrlFromKey(url))
                .description(description).
                        lables(lables.toString()).classifyId(classify).userId(userId).isTop(isTop).build();

        this.getBaseDao().save(resources);
        return null;
    }

    @Override
    public PageBean<Resources> getPageByAlbumId(Long albumId, Integer pageSize, Integer pageNo) {

        System.out.println("pageSize:"+pageSize);
        System.out.println("pageNo:"+pageNo);
        Map<String, Object> param = new HashMap<String, Object>();

        if (albumId !=null)param.put("albumId", albumId);
        System.out.println(param.toString());
        PageHelper.startPage(pageNo, pageSize);
        List<Resources> list = getBaseDao().findList(param);
        PageBean<Resources> page = new PageBean<>(list);
        return page;
    }

    @Override
    protected BaseDao<Resources, Long> getBaseDao() {
        return resourcesDao;
    }

    private PageBean<Resources> page(Integer status,Integer type,Integer isTop,Long classifyId,Integer pageSize,Integer pageNo){

        System.out.println("status:"+status);
        System.out.println("type:"+type);
        System.out.println("isTop:"+isTop);
        System.out.println("classifyId:"+classifyId);
        System.out.println("pageSize:"+pageSize);
        System.out.println("pageNo:"+pageNo);
        Map<String, Object> param = new HashMap<String, Object>();

        if (status !=null)param.put("status", status);
        if (isTop !=null) param.put("isTop", isTop);
        if(type !=null) param.put("type",type);
        if(classifyId!=null) param.put("classifyId",classifyId);
        System.out.println(param.toString());
        PageHelper.startPage(pageNo, pageSize);
        List<Resources> list = getBaseDao().findList(param);
        PageBean<Resources> page = new PageBean<>(list);

        return page;


    }


    private String buildFileUrlFromKey(String key){

            return fileServerPath+"/"+key;
    }

}
