package com.megatron.picserver.controller;

import com.megatron.picserver.jsonBean.AlbumBean;
import com.megatron.picserver.pojo.Album;
import com.megatron.picserver.service.AlbumService;
import com.megatron.picserver.service.ResourcesService;
import com.megatron.picserver.utils.Const;
import com.megatron.picserver.utils.base.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "album")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private ResourcesService resourcesService;


    @RequestMapping("add")
    public Result add(
//                      @RequestParam(value = "name") String name,
//                      @RequestParam(value = "title") String title,
//                      @RequestParam(value = "description") String description,
//                      @RequestParam(value = "imgUrls") List<String> imgUrls
            @RequestBody AlbumBean album
            ) {

       Album entity= albumService.addAlbum(album.getName(),album.getTitle(),album.getDescription(),album.getImgUrls().get(0),album.getIsTop(),album.getIsBanner(),album.getClassifyId(),null,1L);
        album.getImgUrls().stream().forEach(v->{
            resourcesService.add(entity.getId(),album.getTitle(), Const.IMAGE_TYPE,v,null,album.getDescription(),null,album.getClassifyId(),1L,Const.IS_TOP_OFF);
                }
        );

        return new Result(entity);
    }


    @GetMapping("page/top")
    public Result albumTopList(@RequestParam(value = "pageSize") Integer pageSize,
                            @RequestParam(value = "pageNo") Integer pageNo
                            ){

       return new Result(albumService.page(Const.STATUS_NORMAL,null,null,Const.IS_TOP_ON,pageSize,pageNo));

    }

    @GetMapping("list/banner")
    public Result albumBannerList( ){

        return new Result(albumService.list(Const.STATUS_NORMAL,null,Const.IS_BANNER_ON,null));

    }


    @GetMapping("page/classify")
    public Result albumTopList(@RequestParam(value = "pageSize") Integer pageSize,
                               @RequestParam(value = "pageNo") Integer pageNo,
                               @RequestParam(value = "classifyId") Long classifyId
    ){

        return new Result(albumService.page(Const.STATUS_NORMAL,classifyId,null,null,pageSize,pageNo));

    }

}
