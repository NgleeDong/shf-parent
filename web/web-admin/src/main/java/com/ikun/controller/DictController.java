package com.ikun.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ikun.entity.Dict;
import com.ikun.result.Result;
import com.ikun.service.DictService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/dict")
public class DictController {

    @Reference
    private DictService dictService;

    //去展示数据字典页面
    @RequestMapping
    public String index() {
        return "dict/index";
    }

    @ResponseBody
    @GetMapping("/findZnodes")
    public Result findZnodes(@RequestParam(value = "id", defaultValue = "0") Long id) {
        List<Map<String, Object>> znodes = dictService.findZnodes(id);
        return Result.ok(znodes);
    }

    //根据父id获取所有子节点
    @ResponseBody
    @RequestMapping("/findListByParentId/{id}")
    public Result findListByParentId(@PathVariable("id") Long areaId) {
        List<Dict> dictList = dictService.getListByParentId(areaId);
        return Result.ok(dictList);
    }
}
