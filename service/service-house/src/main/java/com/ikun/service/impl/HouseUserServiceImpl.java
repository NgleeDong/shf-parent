package com.ikun.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ikun.entity.HouseUser;
import com.ikun.mapper.BaseMapper;
import com.ikun.mapper.HouseUserMapper;
import com.ikun.service.HouseUserService;
import com.ikun.service.Impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = HouseUserService.class)
@Transactional
public class HouseUserServiceImpl extends BaseServiceImpl<HouseUser> implements HouseUserService {

    @Autowired
    protected HouseUserMapper houseUserMapper;

    @Override
    protected BaseMapper<HouseUser> getEntityMapper() {
        return houseUserMapper;
    }

    @Override
    public List<HouseUser> getHouseUserById(Long id) {
        return houseUserMapper.getHouseUserById(id);
    }
}
