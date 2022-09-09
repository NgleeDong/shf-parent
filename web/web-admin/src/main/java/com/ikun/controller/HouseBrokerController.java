package com.ikun.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.ikun.entity.Admin;
import com.ikun.entity.HouseBroker;
import com.ikun.service.AdminService;
import com.ikun.service.HouseBrokerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/houseBroker")
public class HouseBrokerController {
    public static final String SUCCESS_PAGE = "common/successPage";

    @Reference
    private HouseBrokerService houseBrokerService;

    @Reference
    private AdminService adminService;

    //去添加经纪人的页面
    @RequestMapping("/create")
    public String goCreate(@RequestParam("houseId") Long houseId, Map map) {
        //房源id放入Request域中
        map.put("houseId", houseId);
        //获取所有用户
        List<Admin> adminList = adminService.findAll();
        map.put("adminList", adminList);
        return "houseBroker/create";
    }

    //保存经纪人
    @RequestMapping("/save")
    public String save(HouseBroker houseBroker) { //pojo入参
        //根据经纪人的id查询经纪人信息，调用adminService
        Admin admin = adminService.getById(houseBroker.getBrokerId());
        houseBroker.setBrokerName(admin.getName());
        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());
        //调用houseBrokerService
        houseBrokerService.insert(houseBroker);
        //去成功页面
        return SUCCESS_PAGE;
    }

    //去修改经纪人的页面
    @RequestMapping("/edit/{id}")
    public String goEditPage(@PathVariable("id") Long id, Map map) {
        HouseBroker houseBroker = houseBrokerService.getById(id);
        map.put("houseBroker", houseBroker);
        //获取所有用户
        List<Admin> adminList = adminService.findAll();
        map.put("adminList", adminList);
        return "houseBroker/edit";
    }

    //更新经纪人
    @RequestMapping("/update")
    public String update(HouseBroker houseBroker) {
        //根据经纪人的id查询经纪人信息，调用adminService
        Admin admin = adminService.getById(houseBroker.getBrokerId());
        houseBroker.setBrokerName(admin.getName());
        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());
        houseBrokerService.update(houseBroker);
        return SUCCESS_PAGE;
    }

    //删除经纪人
    @RequestMapping("/delete/{houseId}/{brokerId}") //houseId用来重定向
    public String delete(@PathVariable("houseId") Long houseId, @PathVariable("brokerId") Long brokerId) {
        houseBrokerService.delete(brokerId);
        //重定向到查询房源详情的方法
        return "redirect:/house/" + houseId;
    }
}
