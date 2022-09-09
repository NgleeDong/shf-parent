package com.ikun.mapper;

import com.github.pagehelper.Page;
import com.ikun.entity.House;
import com.ikun.vo.HouseQueryVo;
import com.ikun.vo.HouseVo;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseMapper extends BaseMapper<House> {
    Page<HouseVo> findPageList(HouseQueryVo houseQueryVo);
}
