package com.ikun.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ikun.entity.Permission;
import com.ikun.entity.RolePermission;
import com.ikun.helper.PermissionHelper;
import com.ikun.mapper.BaseMapper;
import com.ikun.mapper.PermissionMapper;
import com.ikun.mapper.RolePermissionMapper;
import com.ikun.service.Impl.BaseServiceImpl;
import com.ikun.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = PermissionService.class)
@Transactional
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    protected BaseMapper<Permission> getEntityMapper() {
        return permissionMapper;
    }

    /**
     * 根据角色id获取权限数据
     */
    @Override
    public List<Map<String, Object>> findPermissionByRoleId(Long roleId) {
        //查询到所有的权限列表
        List<Permission> allPermissionList = permissionMapper.findAll();
        //查中间表！！！获取此角色拥有的权限的id
        List<Long> permissionIdListByRoleId = rolePermissionMapper.findPermissionIdListByRoleId(roleId);
        //构建Ztree数据
        List<Map<String, Object>> znodes = new ArrayList<>();
        //遍历
        for (Permission permission : allPermissionList) {
            Map<String,Object> map = new HashMap<>();
            map.put("id", permission.getId());
            map.put("pId", permission.getParentId());
            map.put("name", permission.getName());
            if (permissionIdListByRoleId.contains(permission.getId())) { //证明该权限已分配
                map.put("checked", true);
            }
            znodes.add(map);
        }
        return znodes;
    }

    /**
     * 给角色分配权限 功能实现
     * @param roleId 角色id
     * @param permissionIds 要添加的各种权限的id
     */
    @Override
    public void assignPermission(Long roleId, Long[] permissionIds) {
        // 思路：先根据 role_id 把中间表中的权限数据删除，然后再添加
        //删除
        rolePermissionMapper.deleteByRoleId(roleId);
        //添加
        for (Long permissionId : permissionIds) {
            if (permissionId != null) {
                RolePermission rolePermission = new RolePermission();
                rolePermission.setRoleId(roleId);
                rolePermission.setPermissionId(permissionId);
                //添加到数据库
                rolePermissionMapper.insert(rolePermission);
            }
        }
    }

    /**
     * 通过用户id获取用户的权限
     * @param adminId 用户id
     */
    @Override
    public List<Permission> findMenuPermissionByAdminId(Long adminId) {
        List<Permission> permissionList = null;
        if (adminId == 1L) {
            permissionList = permissionMapper.findAll();
        } else {
            permissionList = permissionMapper.findPermissionListByAdminId(adminId);
        }
        //把权限数据构建成树形结构数据
        List<Permission> result = PermissionHelper.bulid(permissionList);
        return result;
    }


    /**
     * 查询所有的菜单
     */
    @Override
    public List<Permission> findAllMenu() {
        //获取全部权限列表
        List<Permission> allPermissionList = permissionMapper.findAll();
        if (allPermissionList == null) return null;
        //构建树形结构
        List<Permission> result = PermissionHelper.bulid(allPermissionList);
        return result;
    }
}
