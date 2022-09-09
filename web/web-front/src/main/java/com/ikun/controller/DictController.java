package com.ikun.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ikun.entity.Dict;
import com.ikun.result.Result;
import com.ikun.service.DictService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dict")
public class DictController {

    @Reference
    private DictService dictService;

    //根据编码获取所有子节点
    @RequestMapping("/findListByDictCode/{dictCode}")
    public Result findListByDictCode(@PathVariable("dictCode") String dictCode) {
        List<Dict> dictList = dictService.getListByDictCode(dictCode);
        return Result.ok(dictList);
    }


    //根据父id查询所有的子节点
    @RequestMapping("/findListByParentId/{areaId}")
    public Result findListByParentId(@PathVariable("areaId") Long areaId) {
        List<Dict> dictList = dictService.getListByParentId(areaId);
        return Result.ok(dictList);
    }
}

