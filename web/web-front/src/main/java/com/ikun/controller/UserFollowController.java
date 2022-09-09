package com.ikun.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.ikun.entity.UserInfo;
import com.ikun.result.Result;
import com.ikun.service.UserFollowService;
import com.ikun.vo.UserFollowVo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/userFollow")
public class UserFollowController {

    @Reference
    private UserFollowService userFollowService;

    //axios.get('/userFollow/auth/follow/' + this.id)
    //关注房源
    @RequestMapping("auth/follow/{houseId}")
    public Result follow(@PathVariable("houseId") Long houseId, HttpSession session) {
        //关注房源需要先登录，怎么知道有没有登录？拦截器
        //....拦截器....
        //获取UserInfo对象
        UserInfo userInfo = (UserInfo) session.getAttribute("user");
        //调用userFollowService中关注房源的方法
        userFollowService.follow(userInfo.getId(), houseId);
        return Result.ok();
    }


    // axios.get('/userFollow/auth/list/'+pageNum+'/'+this.page.pageSize)
    //查询我关注的房源
    @RequestMapping("/auth/list/{pageNum}/{pageSize}")
    public Result list(@PathVariable("pageNum")Integer pageNum,
                       @PathVariable("pageSize") Integer pageSize,
                       HttpSession session) {
        //从session域中获取userInfo对象
        UserInfo userInfo = (UserInfo) session.getAttribute("user");
        //调用分页查询的方法
        PageInfo<UserFollowVo> pageInfo = userFollowService.findPageList(pageNum, pageSize, userInfo.getId());
        return Result.ok(pageInfo);
    }

    //axios.get('/userFollow/auth/cancelFollow/'+id) id是user_follow表中的id
    //取消关注
    @RequestMapping("/auth/cancelFollow/{id}")
    public Result cancelFollow(@PathVariable("id") Long id) {
        //肯定是登录了的，否则就被拦截器拦下来了
        userFollowService.delete(id);
        return Result.ok();
    }
}
