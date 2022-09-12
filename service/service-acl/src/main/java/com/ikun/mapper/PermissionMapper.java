package com.ikun.mapper;

import com.ikun.entity.Permission;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission> {

    List<Permission> findAll();

    List<Permission> findPermissionListByAdminId(Long adminId);
}
