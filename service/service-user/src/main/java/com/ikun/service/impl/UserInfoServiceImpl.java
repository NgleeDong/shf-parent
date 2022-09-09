package com.ikun.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ikun.entity.UserInfo;
import com.ikun.mapper.BaseMapper;
import com.ikun.mapper.UserInfoMapper;
import com.ikun.service.Impl.BaseServiceImpl;
import com.ikun.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


@Service(interfaceClass = UserInfoService.class)
@Transactional
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfo> implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    protected BaseMapper<UserInfo> getEntityMapper() {
        return userInfoMapper;
    }

    @Override
    public UserInfo getUserInfoByPhone(String phone) {
        return userInfoMapper.getUserInfoByPhone(phone);
    }
}
