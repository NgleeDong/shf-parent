package com.ikun.mapper;

import com.ikun.entity.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMapper extends BaseMapper<Role>{

    public List<Role> findAll();

//    public Integer insert(Role role);
//
//    public Role getRoleById(Long id);
//
//    public Integer update(Role role);
//
//    public Integer delete(Long id);
//
//    /**
//     * 分页及带条件查询
//     */
//    public Page<Role> findPage(Map<String, Object> filters);
}
