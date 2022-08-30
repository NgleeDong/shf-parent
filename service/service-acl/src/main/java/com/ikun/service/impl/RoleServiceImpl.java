package com.ikun.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ikun.entity.Role;
import com.ikun.mapper.BaseMapper;
import com.ikun.mapper.RoleMapper;
import com.ikun.service.Impl.BaseServiceImpl;
import com.ikun.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service(interfaceClass = RoleService.class)
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {


    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> findAll() {
        return roleMapper.findAll();
    }

    @Override
    protected BaseMapper<Role> getEntityMapper() {
        return this.roleMapper;
    }

}
