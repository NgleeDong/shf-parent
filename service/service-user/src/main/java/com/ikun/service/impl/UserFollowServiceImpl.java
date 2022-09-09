package com.ikun.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ikun.entity.UserFollow;
import com.ikun.mapper.BaseMapper;
import com.ikun.mapper.UserFollowMapper;
import com.ikun.service.DictService;
import com.ikun.service.Impl.BaseServiceImpl;
import com.ikun.service.UserFollowService;
import com.ikun.vo.UserFollowVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


@Service(interfaceClass = UserFollowService.class)
@Transactional
public class UserFollowServiceImpl extends BaseServiceImpl<UserFollow> implements UserFollowService {

    @Autowired
    private UserFollowMapper userFollowMapper;

    //新知识点★===================================================
    //UserFollowService消费dictService
    @Reference
    private DictService dictService;

    @Override
    protected BaseMapper<UserFollow> getEntityMapper() {
        return userFollowMapper;
    }

    @Override
    public void follow(Long id, Long houseId) {
        //创建UserFollow对象
        UserFollow userFollow = new UserFollow();
        userFollow.setUserId(id);
        userFollow.setHouseId(houseId);
        userFollowMapper.insert(userFollow);
    }

    @Override
    public Boolean isFollowed(Long userId, Long houseId) {
        //调用userFollowMapper中的方法，看数量就行
        Integer count = userFollowMapper.getCountByUserIdAndHouseId(userId, houseId);
        //count > 0 证明已经关注了该房源
        return count > 0 ? true : false;
    }

    @Override
    public PageInfo<UserFollowVo> findPageList(Integer pageNum, Integer pageSize, Long userId) {
        //开启分页
        PageHelper.startPage(pageNum, pageSize);
        Page<UserFollowVo> page = userFollowMapper.findPageList(userId);
        //遍历page！！！ 对某些查到的id属性-->名字
        for (UserFollowVo userFollowVo : page) {
            String houseTypeName = dictService.getNameById(userFollowVo.getHouseTypeId());
            String floorName = dictService.getNameById(userFollowVo.getFloorId());
            String directionName = dictService.getNameById(userFollowVo.getDirectionId());
            userFollowVo.setHouseTypeName(houseTypeName);
            userFollowVo.setFloorName(floorName);
            userFollowVo.setDirectionName(directionName);
        }
        return new PageInfo<>(page, 5);
    }
}
