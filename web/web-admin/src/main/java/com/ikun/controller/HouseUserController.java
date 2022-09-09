package com.ikun.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.ikun.entity.HouseUser;
import com.ikun.service.HouseUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/houseUser")
public class HouseUserController {
    public static final String SUCCESS_PAGE = "common/successPage";

    @Reference
    private HouseUserService houseUserService;


    //去新增页面
    @RequestMapping("/create")
    public String toCreate(@RequestParam("houseId") Long houseId, Map map) {
        map.put("houseId", houseId);
        return "houseUser/create";
    }

    //新增房东
    @RequestMapping("/save")
    public String save(HouseUser houseUser) {
        houseUserService.insert(houseUser);
        return SUCCESS_PAGE;
    }

    //去修改的页面
    @RequestMapping("/edit/{id}")
    public String toEdit(@PathVariable("id") Long id, Map map) {
        //通过id查询房东信息
        HouseUser houseUser = houseUserService.getById(id);
        map.put("houseUser", houseUser);
        return "houseUser/edit";
    }

    //更新
    @RequestMapping("/update")
    public String update(HouseUser houseUser) {
        houseUserService.update(houseUser);
        return SUCCESS_PAGE;
    }

    //删除
    @RequestMapping("/delete/{houseId}/{id}") //houseId用来重定向
    public String delete(@PathVariable("houseId") Long houseId, @PathVariable("id") Long id) {
        houseUserService.delete(id);
        //重定向到详情页面
        return "redirect:/house/" + houseId;
    }
}
