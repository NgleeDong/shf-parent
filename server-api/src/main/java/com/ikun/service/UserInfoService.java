package com.ikun.service;

import com.ikun.entity.UserInfo;

public interface UserInfoService extends BaseService<UserInfo> {
    UserInfo getUserInfoByPhone(String phone);
}
