package com.ikun.service;

import com.github.pagehelper.PageInfo;
import com.ikun.entity.UserFollow;
import com.ikun.vo.UserFollowVo;

public interface UserFollowService extends BaseService<UserFollow> {
    /**
     * 关注房源
     * @param id 用户id
     * @param houseId 房源id
     */
    void follow(Long id, Long houseId);

    /**
     * 查看是否关注该房源
     * @param userId 用户id
     * @param houseId 房源id
     * @return
     */
    Boolean isFollowed(Long userId, Long houseId);

    /**
     * 分页查询我关注的房源
     * @param pageNum
     * @param pageSize
     * @param userId
     * @return
     */
    PageInfo<UserFollowVo> findPageList(Integer pageNum, Integer pageSize, Long userId);
}
