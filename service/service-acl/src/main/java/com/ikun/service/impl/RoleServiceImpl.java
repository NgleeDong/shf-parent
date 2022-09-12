package com.ikun.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ikun.entity.AdminRole;
import com.ikun.entity.Role;
import com.ikun.mapper.AdminRoleMapper;
import com.ikun.mapper.BaseMapper;
import com.ikun.mapper.RoleMapper;
import com.ikun.service.Impl.BaseServiceImpl;
import com.ikun.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service(interfaceClass = RoleService.class)
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {


    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private AdminRoleMapper adminRoleMapper;

    @Override
    public List<Role> findAll() {
        return roleMapper.findAll();
    }

    /**
     * 根据用户获取角色数据
     */
    @Override
    public Map<String, Object> findRoleByAdminId(Long adminId) {
        //查询所有的角色 差acl_role 表
        List<Role> allRoleList = roleMapper.findAll();
        //查询某个用户拥有的角色（查到的是id）
        List<Long> existRoleIdList = adminRoleMapper.findRoleIdByAdminId(adminId);
        //对角色进行分类
        List<Role> assignRoleList = new ArrayList<>();
        List<Role> noAssignRoleList = new ArrayList<>();
        for (Role role : allRoleList) {
            //已经分配
            if (existRoleIdList.contains(role.getId())) {
                assignRoleList.add(role);
            } else {
                noAssignRoleList.add(role);
            }
        }
        //放入map中
        Map<String, Object> map = new HashMap<>();
        map.put("assignRoleList", assignRoleList);
        map.put("noAssignRoleList", noAssignRoleList);
        return map;
    }

    /**
     * 给用户分配角色
     * @param adminId 用户id
     * @param roleIds 要分配的角色id们
     */
    @Override
    public void assignRole(Long adminId, Long[] roleIds) {
        //思路：先删除，再添加，操作的表示 中间表
        //先删除
        adminRoleMapper.delete(adminId);
        //在遍历roleIds，ids是需要添加的
        for (Long roleId : roleIds) {
            if (roleId != null) {
                AdminRole adminRole = new AdminRole();
                adminRole.setAdminId(adminId);
                adminRole.setRoleId(roleId);
                //插入
                adminRoleMapper.insert(adminRole);
            }
        }
    }

    @Override
    protected BaseMapper<Role> getEntityMapper() {
        return this.roleMapper;
    }

}
