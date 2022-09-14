package com.ikun.service;

import com.ikun.entity.Admin;

import java.util.List;

public interface AdminService extends BaseService<Admin> {
    List<Admin> findAll();

    Admin geAdminByUsername(String username);
}
