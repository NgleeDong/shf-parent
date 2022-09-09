package com.ikun.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ikun.entity.Admin;
import com.ikun.mapper.AdminMapper;
import com.ikun.mapper.BaseMapper;
import com.ikun.service.AdminService;
import com.ikun.service.Impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service(interfaceClass = AdminService.class)
public class AdminServiceImpl extends BaseServiceImpl<Admin> implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    protected BaseMapper<Admin> getEntityMapper() {
        return this.adminMapper;
    }

    @Override
    public List<Admin> findAll() {
        return adminMapper.findAll();
    }
}
