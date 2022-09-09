package com.ikun.mapper;

import com.ikun.entity.HouseBroker;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseBrokerMapper extends BaseMapper<HouseBroker> {

    //根据房源id获取经纪人列表
    List<HouseBroker> getHouseBrokerByHouseId(Long id);
}
