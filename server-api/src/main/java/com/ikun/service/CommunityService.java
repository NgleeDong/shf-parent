package com.ikun.service;

import com.ikun.entity.Community;

import java.util.List;

public interface CommunityService extends BaseService<Community> {
    List<Community> findAll();
}
