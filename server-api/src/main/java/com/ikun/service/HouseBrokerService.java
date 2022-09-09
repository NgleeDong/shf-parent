package com.ikun.service;

import com.ikun.entity.HouseBroker;

import java.util.List;

public interface HouseBrokerService extends BaseService<HouseBroker> {

    //根据房源id获取经纪人列表
    List<HouseBroker> getHouseBrokerByHouseId(Long id);
}
