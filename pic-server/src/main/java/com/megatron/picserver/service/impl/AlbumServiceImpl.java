package com.megatron.picserver.service.impl;

import com.github.pagehelper.PageHelper;
import com.megatron.picserver.dao.AlbumDao;
import com.megatron.picserver.pojo.Album;
import com.megatron.picserver.service.AlbumService;
import com.megatron.picserver.utils.Const;
import com.megatron.picserver.utils.base.BaseDao;
import com.megatron.picserver.utils.base.PageBean;
import com.megatron.picserver.utils.base.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service(value ="AlbumService" )
public class AlbumServiceImpl extends BaseServiceImpl<Album,Long> implements AlbumService {

    @Autowired
    private AlbumDao albumDao;


    @Override
    public Album addAlbum(String name, String title, String description, String url, Integer isTop, Integer isBanner, Long classIfyId, List<String> lables, Long userId, String studioToken) {
        Album album= Album.builder().name(name).title(title).description(description).url(url).isTop(isTop)
                .isBanner(isBanner).classifyId(classIfyId).lables(null).userId(1L).status(Const.STATUS_NORMAL).createTime(new Date()).updateTime(new Date()).studioToken(studioToken).build();

        this.getBaseDao().save(album);

        return album;
    }

    @Override
    public boolean updateAlbum(Long id, String name, String title, String description, String url, Integer isTop, Integer isBanner, Long classIfyId, List<String> lables, Long userId) {


        Album album= Album.builder().id(id).name(name).title(title).description(description).url(url).isTop(isTop)
                .isBanner(isBanner).classifyId(classIfyId).lables(lables.toString()).userId(1L).build();

        this.getBaseDao().updateById(album);
        return true;
    }

    @Override
    public PageBean<Album> page(Integer status, Long classifyId, Integer isBanner, Integer isTop, Integer pageSize, Integer pageNo, String studioToken) {
        Map<String, Object> param = new HashMap<>();
        param.put("status", status);
        param.put("classifyId", classifyId);
        param.put("isBanner", isBanner);
        param.put("isTop", isTop);
        param.put("studioToken", studioToken);
        System.out.println(param.toString());
        PageHelper.startPage(pageNo, pageSize);
        List<Album> list= this.getBaseDao().findList(param);
        PageBean<Album> page = new PageBean<>(list);
        return  page;
    }

    @Override
    public List<Album> list(Integer status, Long classifyId, Integer isBanner, Integer isTop, String studioToken) {
        Map<String, Object> param = new HashMap<>();
        param.put("status", status);
        param.put("classifyId", classifyId);
        param.put("isBanner", isBanner);
        param.put("isTop", isTop);
        param.put("studioToken", studioToken);
        System.out.println(param.toString());

        List<Album> list= this.getBaseDao().findList(param);

        return list;
    }

    @Override
    protected BaseDao<Album, Long> getBaseDao() {
        return albumDao;
    }
}
