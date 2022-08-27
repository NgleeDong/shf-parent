package com.ikun.service.impl;

import com.ikun.entity.Admin;
import com.ikun.mapper.AdminMapper;
import com.ikun.mapper.BaseMapper;
import com.ikun.service.AdminService;
import com.ikun.service.Impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("adminService")
public class AdminServiceImpl extends BaseServiceImpl<Admin> implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    protected BaseMapper<Admin> getEntityMapper() {
        return this.adminMapper;
    }
}
