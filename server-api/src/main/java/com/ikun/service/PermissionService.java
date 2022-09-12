package com.ikun.service;

import com.ikun.entity.Permission;

import java.util.List;
import java.util.Map;

public interface PermissionService extends BaseService<Permission> {

    /**
     * 根据角色获取权限数据
     */
    List<Map<String,Object>> findPermissionByRoleId(Long roleId);

    /**
     * 保存角色权限
     */
    void assignPermission(Long roleId, Long[] permissionIds);

    /**
     * 通过用户id获取用户的菜单权限
     */
    List<Permission> findMenuPermissionByAdminId(Long adminId);

    /**
     * 查询所有的菜单
     * @return
     */
    List<Permission> findAllMenu();
}
