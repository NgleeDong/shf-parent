package com.ikun.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ikun.entity.Role;
import com.ikun.mapper.BaseMapper;
import com.ikun.mapper.RoleMapper;
import com.ikun.service.Impl.BaseServiceImpl;
import com.ikun.service.RoleService;
import com.ikun.util.CastUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Service("roleService")
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

//    @Override
//    public Integer insert(Role role) {
//        return roleMapper.insert(role);
//    }
//
//
//
//    @Override
//    public Integer update(Role role) {
//        return roleMapper.update(role);
//    }
//
//    @Override
//    public Role getById(Serializable id) {
//        return roleMapper.getById(id);
//    }
//
//    @Override
//    public void delete(Long id) {
//        roleMapper.delete(id);
//    }
//
//    @Override
//    public PageInfo<Role> findPage(Map<String, Object> filters) {
//        //当前的页数
//        int pageNum = CastUtil.castInt(filters.get("pageNum"), 1);
//        //每页显示的记录条数
//        int pageSize = CastUtil.castInt(filters.get("pageSize"), 3);
//        //开启分页
//        PageHelper.startPage(pageNum, pageSize);
//        //调用mapper中分页及带条件查询的方法
//        Page<Role> page = roleMapper.findPage(filters); //返回结果其实就是个List集合
//        return new PageInfo<>(page, 5);
//    }
}
