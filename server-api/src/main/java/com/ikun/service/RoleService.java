package com.ikun.service;

import com.ikun.entity.Role;

import java.util.List;

public interface RoleService extends BaseService<Role> {

    public List<Role> findAll();

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
