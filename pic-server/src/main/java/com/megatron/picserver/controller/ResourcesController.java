package com.megatron.picserver.controller;

import com.megatron.picserver.service.ResourcesService;
import com.megatron.picserver.utils.Const;
import com.megatron.picserver.utils.base.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "resources")
@Api(description = "图片/视频管理接口")
@Validated
public class ResourcesController {

    @Autowired
    private ResourcesService resourcesService;

    @ApiOperation(value = "新增图片", notes = "调用该方法新增图片")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "title", value = "标题", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(name = "url", value = "图片地址", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(name = "fileName", value = "图片名称", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(name = "description", value = "描述", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(name = "lables", value = "标签", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(name = "classifyId", value = "分类Id", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(name = "isTop", value = "是否推荐", paramType = "query", dataType = "String", required = false)
    })
    @PostMapping(value = "image/add")
    public Result addVideo(
            @RequestParam(value = "title") String title,
            @RequestParam(value = "key") String key,
            @RequestParam(value = "fileName") String fileName,
            @RequestParam(value = "description") String description,
            @RequestParam(value = "lables") List<String> lables,
            @RequestParam(value = "classifyId") Long classifyId,
            @RequestParam(value = "isTop",required = false) Integer isTop,
            HttpServletRequest request

    ) throws IOException {

        return new Result(resourcesService.add(null,title,Const.IMAGE_TYPE,key,fileName,description,lables,classifyId,1L,isTop));

    }
    @ApiOperation(value = "新增视频", notes = "调用该方法新增视频")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "title", value = "标题", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(name = "key", value = "视频地址", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(name = "fileName", value = "视频名称", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(name = "description", value = "描述", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(name = "lables", value = "标签", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(name = "classifyId", value = "分类Id", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(name = "isTop", value = "是否推荐", paramType = "query", dataType = "String", required = false)
    })
    @PostMapping(value = "video/add")
    public Result addImage(
            @RequestParam(value = "title") String title,
            @RequestParam(value = "key") String key,
            @RequestParam(value = "fileName") String fileName,
            @RequestParam(value = "description") String description,
            @RequestParam(value = "lables") List<String> lables,
            @RequestParam(value = "classifyId") Long classifyId,
            @RequestParam(value = "isTop",required = false) Integer isTop,
            HttpServletRequest request

    ) throws IOException {

        return new Result(resourcesService.add(null,title,Const.VIDEO_TYPE,key,fileName,description,lables,classifyId,1L,isTop));

    }


    @ApiOperation(value = "维护图片", notes = "调用该方法维护图片")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id", value = "ID", paramType = "query", dataType = "Integer", required = true),
            @ApiImplicitParam(name = "title", value = "标题", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(name = "fileName", value = "图片名称", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(name = "description", value = "描述", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(name = "lables", value = "标签", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(name = "classifyId", value = "分类Id", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(name = "isTop", value = "是否推荐", paramType = "query", dataType = "String", required = false)
    })
    @PostMapping(value = "update")
    public Result update(
            @RequestParam(value = "id") Long id,
            @RequestParam(value = "title") String title,
            @RequestParam(value = "type") Integer type,
            @RequestParam(value = "fileName") String fileName,
            @RequestParam(value = "description") String description,
            @RequestParam(value = "lables",required = false) List<String> lables,
            @RequestParam(value = "classifyId") Long classifyId,
            @RequestParam(value = "isTop",required = false) Integer isTop,
            HttpServletRequest request

    ) throws IOException {

        return new Result(resourcesService.update(id,title,null,null,description,lables,classifyId,1L,isTop));

    }

    @ApiOperation(value = "查询图片推荐资源", notes = "推荐图片查询接口")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "pageSize", value = "每页显示数量", paramType = "query", dataType = "Integer", defaultValue = "10",required = true),
            @ApiImplicitParam(name = "pageNo", value = "页码", paramType = "query", dataType = "Integer" ,defaultValue = "0", required = true),
    })
    @GetMapping("image/recommenpage")
    public Result recommenpageImage(
            @RequestParam(value = "pageSize") Integer pageSize,
            @RequestParam(value = "pageNo") Integer pageNo
    ){
      return new Result(resourcesService.getRecommendPage(Const.IMAGE_TYPE,pageSize,pageNo));
    }

    @ApiOperation(value = "根据分类ID查询图片", notes = "图片分类查询接口")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "pageSize", value = "每页显示数量", paramType = "query", dataType = "Integer", defaultValue = "10",required = true),
            @ApiImplicitParam(name = "pageNo", value = "页码", paramType = "query", dataType = "Integer" ,defaultValue = "0", required = true),
            @ApiImplicitParam(name = "classifyId", value = "分类ID", paramType = "query", dataType = "Integer" ,defaultValue = "1", required = true),
    })
    @GetMapping("image/pageByClassify")
    public Result imagePageByClassify(
            @RequestParam(value = "classifyId") Long classifyId,
            @RequestParam(value = "pageSize") Integer pageSize,
            @RequestParam(value = "pageNo") Integer pageNo
    ){
        return new Result(resourcesService.getPageByClassify(Const.IMAGE_TYPE,classifyId,pageSize,pageNo));
    }

    @ApiOperation(value = "根据分类ID查询视频", notes = "视频分类查询接口")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "pageSize", value = "每页显示数量", paramType = "query", dataType = "Integer", defaultValue = "10",required = true),
            @ApiImplicitParam(name = "pageNo", value = "页码", paramType = "query", dataType = "Integer" ,defaultValue = "0", required = true),
            @ApiImplicitParam(name = "classifyId", value = "分类ID", paramType = "query", dataType = "Integer" ,defaultValue = "1", required = true),
    })
    @GetMapping("video/pageByClassify")
    public Result videoPageByClassify(
            @RequestParam(value = "classifyId") Long classifyId,
            @RequestParam(value = "pageSize") Integer pageSize,
            @RequestParam(value = "pageNo") Integer pageNo
    ){
        return new Result(resourcesService.getPageByClassify(Const.VIDEO_TYPE,classifyId,pageSize,pageNo));
    }


    @ApiOperation(value = "查询图片", notes = "图片查询接口")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "pageSize", value = "每页显示数量", paramType = "query", dataType = "Integer", defaultValue = "10",required = true),
            @ApiImplicitParam(name = "pageNo", value = "页码", paramType = "query", dataType = "Integer" ,defaultValue = "0", required = true),
    })
    @GetMapping("imagePage")
    public Result pageByClassify(
            @RequestParam(value = "pageSize") Integer pageSize,
            @RequestParam(value = "pageNo") Integer pageNo
    ){
        return new Result(resourcesService.getPageByType(Const.IMAGE_TYPE,pageSize,pageNo));
    }

    @ApiOperation(value = "查询视频", notes = "视频查询接口")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "pageSize", value = "每页显示数量", paramType = "query", dataType = "Integer", defaultValue = "10",required = true),
            @ApiImplicitParam(name = "pageNo", value = "页码", paramType = "query", dataType = "Integer" ,defaultValue = "0", required = true),
    })


    @GetMapping("viedeoPage")
    public Result viedeoPage(
            @RequestParam(value = "pageSize") Integer pageSize,
            @RequestParam(value = "pageNo") Integer pageNo
    ){
        return new Result(resourcesService.getPageByType(Const.VIDEO_TYPE,pageSize,pageNo));
    }


    @GetMapping("albumPage")
    public Result albumPage(@RequestParam(value = "albumId") Long albumId,
            @RequestParam(value = "pageSize") Integer pageSize,
            @RequestParam(value = "pageNo") Integer pageNo
    ){
        return new Result(resourcesService.getPageByAlbumId(albumId,pageSize,pageNo));
    }








}
