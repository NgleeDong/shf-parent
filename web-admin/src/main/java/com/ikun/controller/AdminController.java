package com.ikun.controller;

import com.github.pagehelper.PageInfo;
import com.ikun.entity.Admin;
import com.ikun.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController{

    public static final String SUCCESS_PAGE = "common/successPage";

    @Autowired
    private AdminService adminService;

    /**
     * 分页及待条件的查询
     */
    @RequestMapping
    public String findPage(Map map, HttpServletRequest request) {
        //获取请求参数
        Map<String, Object> filters = getFilters(request);
        map.put("filters", filters);
        //调用adminService中分页的方法
        PageInfo<Admin> pageInfo = adminService.findPage(filters);
        map.put("page", pageInfo);
        //去admin/index.html
        return "admin/index";
    }

    /**
     * 新增用户，点击按钮去新增页面
     */
    @RequestMapping("/create")
    public String toCreate() {
        //去新增页面
        return "admin/create";
    }

    /**
     * 新增用户的实现
     * 表单提交：POST方式
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveAdmin(Admin admin) { //pojo入参
        adminService.insert(admin);
        //弹窗的需要进入SUCCESS_PAGE，不弹窗的则是重定向
        return SUCCESS_PAGE;
    }

    /**
     * 更新用户信息，点击按钮去新增页面
     */
    @RequestMapping("/edit/{id}")
    public String toEdit(@PathVariable("id") Long id, Map map) {
        //第一步，要根据id查询到操作的用户
        Admin admin = adminService.getById(id);
        //第二步：将查到的map放入请求域中，因为edit页面要回显admin的信息
        map.put("admin", admin);
        //第三步:以弹窗的方式打开admin/edit.html
        return "admin/edit";
    }

    /**
     * 更新用户的实现
     * 表单提交：POST方式
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(Admin admin) { //pojo入参
        adminService.update(admin);
        //弹窗的需要进入SUCCESS_PAGE，不弹窗的则是重定向
        return SUCCESS_PAGE;
    }


    /**
     * 根据id删除：逻辑删除
     * id是前台页面根据传过来的
     * 注意：此处是get请求，没有表单！前台页面只是一个a标签
     */
    @RequestMapping(value = "/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        adminService.delete(id);
        //不弹窗的：重定向到查询所有角色的方法
        return "redirect:/admin";
    }

}
