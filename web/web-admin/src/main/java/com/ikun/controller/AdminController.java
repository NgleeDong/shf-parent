package com.ikun.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.ikun.entity.Admin;
import com.ikun.entity.HouseImage;
import com.ikun.result.Result;
import com.ikun.service.AdminService;
import com.ikun.service.RoleService;
import com.ikun.util.QiniuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController{

    public static final String SUCCESS_PAGE = "common/successPage";

    @Reference
    private AdminService adminService;

    @Reference
    private RoleService roleService;

    /**
     * 分页及带条件的查询
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

    /**
     * 去上传头像的页面
     */
    @RequestMapping("/uploadShow/{id}")
    public String toUpLoad(@PathVariable("id") Long id, Map map) {
        map.put("id", id);
        return "admin/upload";
    }

    /**
     * 上传头像
     */

    @RequestMapping("/upload/{id}")
    public String upload(@PathVariable("id") Long id,
                         @RequestParam("file") MultipartFile[] files) {
        try {
            if (files != null && files.length > 0) {
                for (MultipartFile file : files) {
                    //获取字节数组
                    byte[] bytes = file.getBytes();
                    //获取图片的名字（真实的名字）
                    String filename = file.getOriginalFilename();
                    //通过UUID随机生成一个字符串
                    String newFileName = UUID.randomUUID().toString();
                    //通过Qiniu工具类上传图片到七牛云
                    QiniuUtil.upload2Qiniu(bytes, newFileName);
                    //创建Admin对象
                    Admin admin = new Admin();
                    admin.setId(id);
                    //设置图片路径
                    //路径的组成：http://七牛云的域名/随机生成的文件新名字
                    admin.setHeadUrl("http://rhs87z6s9.hn-bkt.clouddn.com/" + newFileName);
                    //更新
                    adminService.update(admin);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS_PAGE;
    }

    /**
     * 去分配角色的页面
     */
    //opt.openWin('/admin/assignShow/'+id,'分配角色',550,450)
    @RequestMapping("/assignShow/{adminId}")
    public String assignShow(@PathVariable("adminId") Long adminId, ModelMap modelMap) {
        //需要带三个东西：adminId、assignRoleList、unAssignRoleList
        Map<String, Object> roleMap = roleService.findRoleByAdminId(adminId);
        modelMap.addAllAttributes(roleMap);
        modelMap.addAttribute("adminId", adminId);
        return "admin/assignShow";
    }

    /**
     * 给用户分配角色功能实现
     */
    @RequestMapping("/assignRole")
    public String assignRole(@RequestParam("adminId") Long adminId,
                             @RequestParam("roleIds") Long[] roleIds) {
        roleService.assignRole(adminId, roleIds);
        return SUCCESS_PAGE;
    }
}
