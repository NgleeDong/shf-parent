package com.ikun.mapper;

import com.ikun.entity.HouseImage;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseImageMapper extends BaseMapper<HouseImage> {

    //根据房源id和图片类型获取房源、房产图片
    List<HouseImage> getHouseImageByIdAndType(Long id, Integer type);
}
