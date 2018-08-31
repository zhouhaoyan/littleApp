package com.megatron.picserver.controller;

import com.megatron.picserver.service.ResourceLogService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: ResourceLogController
 * @Description: TODO
 * @Author: zhouhaoyan
 * @date: 2018/8/31 5:49 PM
 * @version: 1.0
 **/

@RestController
@RequestMapping(value = "resourcesLog")
@Api(description = "资源锚点接口")
@Validated
public class ResourceLogController {

    @Autowired
    private ResourceLogService resourceLogService;

    @GetMapping(value = "look")
    public boolean updateUserInfo(@RequestParam(value = "rId") Long rId, @RequestParam(value = "type") String type,
                                  @RequestParam(value = "openId", required = false) String openId) {

        return resourceLogService.addRecord(rId, type, openId);

    }


}
