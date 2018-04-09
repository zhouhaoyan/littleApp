package com.megatron.picserver.controller;

import com.megatron.picserver.pojo.Classify;
import com.megatron.picserver.service.ClassifyService;
import com.megatron.picserver.utils.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "classify")
public class ClassifyController {

    @Autowired
    private ClassifyService classifyService;

    @GetMapping("list")
    public List<Classify>  list(){

       return classifyService.list(Const.STATUS_NORMAL);
    }
}
