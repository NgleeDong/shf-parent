package com.ikun.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ikun.entity.HouseImage;
import com.ikun.mapper.BaseMapper;
import com.ikun.mapper.HouseImageMapper;
import com.ikun.service.HouseImageService;
import com.ikun.service.Impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = HouseImageService.class)
@Transactional
public class HouseImageServiceImpl extends BaseServiceImpl<HouseImage> implements HouseImageService {

    @Autowired
    protected HouseImageMapper houseImageMapper;

    @Override
    protected BaseMapper<HouseImage> getEntityMapper() {
        return houseImageMapper;
    }

    @Override
    public List<HouseImage> getHouseImageByIdAndType(Long id, Integer type) {
        return houseImageMapper.getHouseImageByIdAndType(id, type);
    }
}
