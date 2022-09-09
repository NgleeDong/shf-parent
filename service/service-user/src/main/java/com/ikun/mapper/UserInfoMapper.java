package com.ikun.mapper;

import com.ikun.entity.UserInfo;

public interface UserInfoMapper extends BaseMapper<UserInfo> {
    UserInfo getUserInfoByPhone(String phone);
}
