package com.ikun.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ikun.entity.Community;
import com.ikun.mapper.BaseMapper;
import com.ikun.mapper.CommunityMapper;
import com.ikun.mapper.DictMapper;
import com.ikun.service.CommunityService;
import com.ikun.service.Impl.BaseServiceImpl;
import com.ikun.util.CastUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = CommunityService.class)
@Transactional
public class CommunityServiceImpl extends BaseServiceImpl<Community> implements CommunityService {

    @Autowired
    private CommunityMapper communityMapper;

    @Autowired
    private DictMapper dictMapper;

    @Override
    protected BaseMapper<Community> getEntityMapper() {
        return communityMapper;
    }

    @Override
    //重写findPage方法，因为目前查到的结果没有区域和版块
    public PageInfo<Community> findPage(Map<String, Object> filters) {
        //当前页数
        int pageNum = CastUtil.castInt(filters.get("pageNum"), 1);
        //每页显示的记录条数
        int pageSize = CastUtil.castInt(filters.get("pageSize"), 10);
        //开启分页
        PageHelper.startPage(pageNum, pageSize);
        //调用communityMapper中分页及待条件查询的方法
        //page就是一个List
        Page<Community> page = communityMapper.findPage(filters);
        for (Community community : page) {
            //根据区域的id获取区域的名字
            String areaName = dictMapper.getNameById(community.getAreaId());
            //根据版块的id获取板块的名字
            String plateName = dictMapper.getNameById(community.getPlateId());
            //给小区对象设置区域名和版块名
            community.setAreaName(areaName);
            community.setPlateName(plateName);
        }

        return new PageInfo<>(page, 10);
    }

    @Override
    public List<Community> findAll() {
        return communityMapper.findAll();
    }

    @Override
    public Community getById(Serializable id) {
        Community community = communityMapper.getById(id);
        //根据区域的id获取区域的名字
        String areaName = dictMapper.getNameById(community.getAreaId());
        //根据版块的id获取板块的名字
        String plateName = dictMapper.getNameById(community.getPlateId());
        //给小区对象设置区域名和版块名
        community.setAreaName(areaName);
        community.setPlateName(plateName);
        return community;
    }
}
