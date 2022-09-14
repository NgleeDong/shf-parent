package com.ikun.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ikun.entity.Admin;
import com.ikun.entity.Permission;
import com.ikun.service.AdminService;
import com.ikun.service.PermissionService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    @Reference
    private AdminService adminService;

    @Reference
    private PermissionService permissionService;

    /**
     * 后台首页
     */
//    @RequestMapping("/")
//    public String toIndex() {
//        return "frame/index";
//    }

    /**
     * 后台首页
     */
    @RequestMapping("/")
    public String toIndex(Map map) {
        //设置默认的用户id
//        Long userId = 1L;//超级管理员
        //页面要显示名字，所以
//        Admin admin = adminService.getById(userId);

        //通过Spring Security 获取User对象
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //根据用户名获取admin
        Admin admin = adminService.geAdminByUsername(user.getUsername());
        //放入request域
        map.put("admin", admin);
        List<Permission> permissionList = permissionService.findMenuPermissionByAdminId(admin.getId());
        map.put("permissionList", permissionList);
        return "frame/index";
    }

    /**
     * 后台的主页，用iframe内联框架实现的
     */
    @RequestMapping("/main")
    public String toMain() {
        return "frame/main";
    }

    /**
     * 登录页面
     */
    @RequestMapping("/login")
    public String login() {
        return "frame/login";
    }

    /**
     * 登出，不需要写映射
     */
//    @RequestMapping("/logout")
//    public String logout(HttpSession session) {
//        //让session失效
//        session.invalidate();
//        //重定向
//        return "redirect:/login";
//    }

    /**
     *  未授权的统一处理方式
     */
    @GetMapping("/auth")
    public String auth() {
        return "frame/auth";
    }

}
