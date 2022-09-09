package com.ikun.service;

import com.ikun.entity.HouseImage;

import java.util.List;

public interface HouseImageService extends BaseService<HouseImage> {

    //根据房源id和图片类型获取房源、房产图片
    List<HouseImage> getHouseImageByIdAndType(Long id, Integer type);
}
