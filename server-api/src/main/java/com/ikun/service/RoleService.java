package com.ikun.service;

import com.ikun.entity.Role;

import java.util.List;
import java.util.Map;

public interface RoleService extends BaseService<Role> {

    public List<Role> findAll();

    /**
     * 根据用户id获取角色数据
     */
    Map<String, Object> findRoleByAdminId(Long adminId);

    /**
     * 给用户分配角色
     * @param adminId 用户id
     * @param roleIds 要分配的角色id们
     */
    void assignRole(Long adminId, Long[] roleIds);

//    public Integer insert(Role role);
//
//    public Role getRoleById(Long id);
//
//    public Integer update(Role role);
//
//    public void delete(Long id);
//
//    public PageInfo<Role> findPage(Map<String, Object> filters);
}
