package com.ikun.service;

import com.github.pagehelper.PageInfo;
import com.ikun.entity.House;
import com.ikun.vo.HouseQueryVo;
import com.ikun.vo.HouseVo;

import java.io.Serializable;

public interface HouseService extends BaseService<House> {
    void publish(Long id, Integer status);


    public PageInfo<HouseVo> findPageList(Integer pageNum, Integer pageSize, HouseQueryVo houseQueryVo);
}
