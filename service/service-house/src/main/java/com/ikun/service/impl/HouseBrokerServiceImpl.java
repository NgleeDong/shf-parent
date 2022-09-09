package com.ikun.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ikun.entity.HouseBroker;
import com.ikun.mapper.BaseMapper;
import com.ikun.mapper.HouseBrokerMapper;
import com.ikun.service.HouseBrokerService;
import com.ikun.service.Impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = HouseBrokerService.class)
@Transactional
public class HouseBrokerServiceImpl extends BaseServiceImpl<HouseBroker> implements HouseBrokerService {

    @Autowired
    private HouseBrokerMapper houseBrokerMapper;

    @Override
    protected BaseMapper<HouseBroker> getEntityMapper() {
        return houseBrokerMapper;
    }

    @Override
    public List<HouseBroker> getHouseBrokerByHouseId(Long id) {
        return houseBrokerMapper.getHouseBrokerByHouseId(id);
    }
}
