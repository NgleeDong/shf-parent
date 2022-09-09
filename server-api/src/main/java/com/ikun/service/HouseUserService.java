package com.ikun.service;

import com.ikun.entity.HouseUser;

import java.util.List;

public interface HouseUserService extends BaseService<HouseUser> {

    //根据房源id查询房东信息
    List<HouseUser> getHouseUserById(Long id);
}
