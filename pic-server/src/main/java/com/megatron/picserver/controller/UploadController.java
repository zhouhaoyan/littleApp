package com.megatron.picserver.controller;

import com.google.gson.Gson;

import com.megatron.picserver.service.FileUploadService;
import com.megatron.picserver.utils.UploadUtil;
import com.megatron.picserver.utils.base.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("upload")
@Api(description = "图片/视频管理接口")
@Validated
public class UploadController {

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private UploadUtil uploadUtil;

    @Autowired
    private Gson gson;

    @Value("${IMAGE_PATH}")
    private String imagePath;

    @ApiOperation(value = "文件上传", notes = "建议前端实现七牛云文件上传方法.直接上传至七牛云对象存储")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "file", value = "文件", paramType = "query", dataType = "File", required = true),
    })
    @PostMapping("file")
    public Result fileUpload(@RequestParam("file") MultipartFile file) throws Exception {
        String res="";
        Map<String,Object> result=   fileUploadService.upload(file,file.getOriginalFilename(),imagePath,1L);
        if(result!=null){
            res=uploadUtil.upload(result.get("url").toString(),result.get("fileName").toString());
        }
     return new Result(gson.toJson(res));

}


    @ApiOperation(value = "获取上传凭证", notes = "前端直接上传至七牛云存储时所需要的上传凭证请求接口")
    @GetMapping("uploadToken")
    public String getToken()  {
            return uploadUtil.getUpToken();

    }

}

