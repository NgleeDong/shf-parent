package com.ikun.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.ikun.entity.Role;
import com.ikun.service.PermissionService;
import com.ikun.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {
    public static final String SUCCESS_PAGE = "common/successPage";

    @Reference
    private RoleService roleService;

    @Reference
    private PermissionService permissionService;

//    @RequestMapping
//    public String findAll(Map map) {
//        List<Role> roleList = roleService.findAll();
//        //放到请求域中
//        map.put("list", roleList);
//        return "role/index";
//    }

    /**
     * 分页及带条件查询
     */
    @RequestMapping
    public String index(Map map, HttpServletRequest request) {
        //获取请求参数
        Map<String, Object> filters = getFilters(request);
        //将请求参数放入Request域中
        map.put("filters", filters);
        //调用RoleService层中分页及带条件查询的方法
        PageInfo<Role> pageInfo = roleService.findPage(filters);
        //将pageInfo对象放入Request域中
        map.put("page", pageInfo);
        //去index.html页面
        return "role/index";
    }


    /**
     * 处理请求 /role/create,去添加角色的页面
     * @return
     */
    @RequestMapping("/create")
    public String toCreate() {
        return "role/create";
    }

    /**
     * 处理请求 /role/save
     * 表单默认以 application/x-www-form-urlencoded 方式提交数据
     * 提交的数据按照 key1=val1&key2=val2 的方式进行编码，key 和 val 都进行了 URL 转码
     * 解析之后：
     *      roleName: 测试2
     *      roleCode: 测试2
     *      description: 测试第二次
     * 问题二：为什么不用把RequestMapping的method中的方法改为POST？
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String toSave(Role role) { //入参是pojo
        roleService.insert(role);
        //重定向到查询所有角色的方法
//        return "redirect:/role";
        //去成功页面,看起来更友好，通过js帮我们回到父页面并刷新
        return SUCCESS_PAGE;
    }

    /**
     * 点击修改，根据id查询Role，放入请求域中，去修改页面
     */
    @RequestMapping("/edit/{id}")
    public String toEdit(@PathVariable("id") Long id, ModelMap modelMap) {
        Role role = roleService.getById(id); //获取Role
        //放入请求域中
        modelMap.addAttribute("role", role); //key是html页面中需要的,需要回显role的信息
        return "role/edit"; //去edit.html
    }

    /**
     * 处理修改页面点击确认后的请求：进入成功页面
     * 同样也是表单提交：POST方式
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(Role role) { //pojo入参
        roleService.update(role);
        return SUCCESS_PAGE;
    }

    /**
     * 根据id删除：逻辑删除
     * id是前台页面根据传过来的
     * 注意：此处是get请求，没有表单！前台页面只是一个a标签
     */
    @RequestMapping(value = "/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        roleService.delete(id);
//        return SUCCESS_PAGE; 不去这个页面，只有弹窗才去这个页面
        //重定向到查询所有角色的方法
        return "redirect:/role";
    }



    /**
     * 封装页面提交的分页参数及搜索条件
     * @param request
     * @return
     */
//    private Map<String, Object> getFilters(HttpServletRequest request) {
//        Enumeration<String> paramNames = request.getParameterNames();
//        Map<String, Object> filters = new TreeMap();
//        while(paramNames != null && paramNames.hasMoreElements()) {
//            String paramName = (String)paramNames.nextElement();
//            String[] values = request.getParameterValues(paramName);
//            if (values != null && values.length != 0) {
//                if (values.length > 1) {
//                    filters.put(paramName, values);
//                } else {
//                    filters.put(paramName, values[0]);
//                }
//            }
//        }
//        if(!filters.containsKey("pageNum")) {
//            filters.put("pageNum", 1);
//        }
//        if(!filters.containsKey("pageSize")) {
//            filters.put("pageSize", 3);
//        }
//
//        return filters;
//    }


    /**
     * 去给角色分配权限的页面
     */
    //opt.openWin("/role/assignShow/"+id,'修改',580,430);
    @RequestMapping("/assignShow/{roleId}")
    public String assignShow(@PathVariable("roleId") Long roleId, ModelMap modelMap) {
        //传过去两个东西，roleId与zNodes
        //调用根据角色id获取权限的方法
        List<Map<String, Object>> zNodes = permissionService.findPermissionByRoleId(roleId);
        modelMap.addAttribute("zNodes",zNodes);
        modelMap.addAttribute("roleId", roleId);
        return "role/assignShow";
    }

    /**
     * 给角色分配权限功能实现
     */
    @RequestMapping("/assignPermission")
    public String assignPermission(@RequestParam("roleId") Long roleId,
                                   @RequestParam("permissionIds") Long[] permissionIds) {
        //思路也是先删除后添加
        permissionService.assignPermission(roleId, permissionIds);
        return SUCCESS_PAGE;
    }

}
