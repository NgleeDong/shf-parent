package com.ikun.mapper;

import com.ikun.entity.Community;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityMapper extends BaseMapper<Community> {
    List<Community> findAll();
    //涉及增删改查 需继承BaseMapper
}
