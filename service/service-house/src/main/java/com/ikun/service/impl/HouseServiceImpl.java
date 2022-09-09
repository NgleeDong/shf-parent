package com.ikun.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ikun.entity.House;
import com.ikun.mapper.BaseMapper;
import com.ikun.mapper.DictMapper;
import com.ikun.mapper.HouseMapper;
import com.ikun.service.HouseService;
import com.ikun.service.Impl.BaseServiceImpl;
import com.ikun.vo.HouseQueryVo;
import com.ikun.vo.HouseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Service(interfaceClass = HouseService.class)
@Transactional
public class HouseServiceImpl extends BaseServiceImpl<House> implements HouseService {

    @Autowired
    private HouseMapper houseMapper;
    
    @Autowired
    private DictMapper dictMapper;

    @Override
    protected BaseMapper<House> getEntityMapper() {
        return houseMapper;
    }

    @Override
    public void publish(Long id, Integer status) {
        //创建一个house对象
        House house = new House();
        //设置id
        house.setId(id);
        //设置status
        house.setStatus(status);
        //调用mapper层更新的方法
        houseMapper.update(house);
    }

    public PageInfo<HouseVo> findPageList(Integer pageNum, Integer pageSize, HouseQueryVo houseQueryVo) {
        //开启分页
        PageHelper.startPage(pageNum, pageSize);
        Page<HouseVo> page = houseMapper.findPageList(houseQueryVo);
        //查询出来之后，需要对其中的一些属性根据id查到它得名字，如房屋类型等，故需要遍历
        for (HouseVo houseVo : page) {
            houseVo.setHouseTypeName(dictMapper.getNameById(houseVo.getHouseTypeId()));
            houseVo.setFloorName(dictMapper.getNameById(houseVo.getFloorId()));
            houseVo.setDirectionName(dictMapper.getNameById(houseVo.getDirectionId()));
        }
        return new PageInfo<>(page, 5);
    }


    //重写改方法目的是为了显示详情页面中的户型信息、楼层信息等，因为数据库中存放的是响应的id值
    @Override
    public House getById(Serializable id) {
        House house = houseMapper.getById(id);
        //查询数字字典
        //获取户型
        String houseTypeName = dictMapper.getNameById(house.getHouseTypeId());
        //获取楼层
        String floorName = dictMapper.getNameById(house.getFloorId());
        //获取朝向
        String directionName = dictMapper.getNameById(house.getDirectionId());
        //获取建筑结构
        String buildStructureName = dictMapper.getNameById(house.getBuildStructureId());
        //获取装修情况
        String decorationName = dictMapper.getNameById(house.getDecorationId());
        //获取房屋的用途
        String houseUseName = dictMapper.getNameById(house.getHouseUseId());
        //设置
        house.setHouseTypeName(houseTypeName);
        house.setFloorName(floorName);
        house.setDirectionName(directionName);
        house.setBuildStructureName(buildStructureName);
        house.setDecorationName(decorationName);
        house.setHouseUseName(houseUseName);

        return house;
    }
}
