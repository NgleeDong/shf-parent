package com.ikun.mapper;

import com.ikun.entity.Admin;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminMapper extends BaseMapper<Admin>{
    List<Admin> findAll();
}
