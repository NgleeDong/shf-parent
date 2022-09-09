package com.ikun.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.ikun.entity.Community;
import com.ikun.entity.Dict;
import com.ikun.service.CommunityService;
import com.ikun.service.DictService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/community")
public class CommunityController extends BaseController {

    public static final String SUCCESS_PAGE = "common/successPage";

    @Reference
    private CommunityService communityService;

    @Reference
    private DictService dictService;

    //分页及带条件查询，所以需要继承BaseController
    @RequestMapping
    public String index(Map map, HttpServletRequest request) {
        Map<String, Object> filters = getFilters(request);
        map.put("filters", filters);
        PageInfo<Community> pageInfo = communityService.findPage(filters);
        map.put("page", pageInfo);

        //实现二级联动
        //根据编码获取北京所有的区域
        List<Dict> areaList = dictService.getListByDictCode("beijing");
        //将北京所有的区域放入request域中
        map.put("areaList", areaList);

        //去community目录下的index.html
        return "community/index";
    }

    //去新增界面
    @RequestMapping("/create")
    public String goCreate(Map map) {
        //根据编码获取北京所有的区域
        List<Dict> areaList = dictService.getListByDictCode("beijing");
        //将北京所有的区域放入request域中
        map.put("areaList", areaList);
        //需要带着数据去create.html
        return "community/create";
    }

    //添加小区
    @RequestMapping("/save")
    public String save(Community community) {
        communityService.insert(community);
        return SUCCESS_PAGE;
    }

    //去修改小区的页面
    @RequestMapping("/edit/{id}")
    public String toEdit(@PathVariable("id") Long id, Map map) {
        //根据编码获取北京所有的区域
        List<Dict> areaList = dictService.getListByDictCode("beijing");
        //将北京所有的区域放入request域中
        map.put("areaList", areaList);
        //调用查询方法
        Community community = communityService.getById(id);
        map.put("community", community);
        return "community/edit";
    }

    //更新
    @RequestMapping("/update")
    public String update(Community community) {
        communityService.update(community);
        return SUCCESS_PAGE;
    }

    //删除
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        communityService.delete(id);
        //重定向
        return "redirect:/community";
    }
}
