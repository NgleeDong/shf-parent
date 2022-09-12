package com.ikun.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.ikun.entity.Permission;
import com.ikun.service.PermissionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/permission")
public class PermissionController {
    public static final String SUCCESS_PAGE = "common/successPage";


    @Reference
    private PermissionService permissionService;

    /**
     * 获取菜单列表
     */
    @RequestMapping
    public String toIndex(Map map) {
        List<Permission> list = permissionService.findAllMenu();
        map.put("list", list);
        return "permission/index";
    }


    /**
     * 去新增页面
     */
    //opt.openWin('/permission/create?parentId='+parentId+'&type='+type+'&parentName='+parentName,'新增',630,430)
    @RequestMapping("/create")
    public String toCreate(ModelMap map, Permission permission) { //POJO入参
        map.addAttribute("permission", permission);
        return "permission/create";
    }

    /**
     * 保存新增
     */
    @PostMapping("/save")
    public String save(Permission permission) {
        permissionService.insert(permission);
        return SUCCESS_PAGE;
    }

    /**
     * 去编辑页面
     */
    @GetMapping("/edit/{id}")
    public String toEdit(ModelMap model,@PathVariable("id") Long id) {
        Permission permission = permissionService.getById(id);
        model.addAttribute("permission",permission);
        return "permission/edit";
    }

    /**
     * 保存更新
     */
    @PostMapping(value="/update")
    public String update(Permission permission) {
        permissionService.update(permission);
        return SUCCESS_PAGE;
    }

    /**
     * 删除
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        permissionService.delete(id);
        //重定向
        return "redirect:/permission";
    }
}
