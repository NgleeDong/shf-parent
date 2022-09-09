package com.ikun.mapper;

import com.github.pagehelper.Page;
import com.ikun.entity.UserFollow;
import com.ikun.vo.UserFollowVo;
import org.apache.ibatis.annotations.Param;

public interface UserFollowMapper extends BaseMapper<UserFollow> {
    Integer getCountByUserIdAndHouseId(@Param("userId") Long userId,@Param("houseId") Long houseId);

    Page<UserFollowVo> findPageList(Long userId);
}
