package com.ikun.mapper;

import com.ikun.entity.HouseUser;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseUserMapper extends BaseMapper<HouseUser> {

    //根据房源id查询房东信息
    List<HouseUser> getHouseUserById(Long id);
}
