package com.ikun.mapper;

import com.ikun.entity.AdminRole;

import java.util.List;

public interface AdminRoleMapper extends BaseMapper<AdminRole> {
    List<Long> findRoleIdByAdminId(Long adminId);
}
