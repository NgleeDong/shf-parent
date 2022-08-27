package com.ikun.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ikun.mapper.BaseMapper;
import com.ikun.service.BaseService;
import com.ikun.util.CastUtil;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Map;

//抽象类
@Transactional
public abstract class BaseServiceImpl<T> implements BaseService<T> {

    //抽象方法
    protected abstract BaseMapper<T> getEntityMapper();

    public Integer insert(T t) {
        return getEntityMapper().insert(t);
    }

    public void delete(Long id) {
        getEntityMapper().delete(id);
    }

    public Integer update(T t) {
        return getEntityMapper().update(t);
    }

    public T getById(Serializable id) {
        return getEntityMapper().getById(id);
    }

    public PageInfo<T> findPage(Map<String, Object> filters) {
        //当前页数
        int pageNum = CastUtil.castInt(filters.get("pageNum"), 1);
        //每页显示的记录条数
        int pageSize = CastUtil.castInt(filters.get("pageSize"), 10);

        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<T>(getEntityMapper().findPage(filters), 10);
    }
}
