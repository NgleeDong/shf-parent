package com.ikun.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.ikun.entity.*;
import com.ikun.result.Result;
import com.ikun.service.*;
import com.ikun.vo.HouseQueryVo;
import com.ikun.vo.HouseVo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/house")
public class HouseController {

    @Reference
    private HouseService houseService;

    @Reference
    private CommunityService communityService;//查小区信息
    @Reference
    private HouseImageService houseImageService;//查房源图片
    @Reference
    private HouseBrokerService houseBrokerService;//查经纪人
    @Reference
    private UserFollowService userFollowService;

//    axios.post('/house/list/'+pageNum+'/'+this.page.pageSize, this.houseQueryVo) this.houseQueryVo作为请求体
    //分页及带条件的查询
    @RequestMapping("/list/{pageNum}/{pageSize}")
    public Result findPageList(@RequestBody HouseQueryVo houseQueryVo,
                               @PathVariable("pageNum") Integer pageNum,
                               @PathVariable("pageSize") Integer pageSize) {
        PageInfo<HouseVo> pageInfo = houseService.findPageList(pageNum, pageSize, houseQueryVo);
        return Result.ok(pageInfo);
    }

    //查看房源详情
    @RequestMapping("/info/{id}")
    public Result info(@PathVariable("id") Long id, HttpSession session) {
        //获取房源信息
        House house = houseService.getById(id);
        //获取小区信息
        Community community = communityService.getById(house.getCommunityId());
        //获取房源图片
        List<HouseImage> houseImage1List = houseImageService.getHouseImageByIdAndType(id, 1);
        //获取房源经纪人
        List<HouseBroker> houseBrokerList = houseBrokerService.getHouseBrokerByHouseId(id);
        //不需要放入Request域，创建一个Map
        Map map = new HashMap<>();
        map.put("house", house);
        map.put("community", community);
        map.put("houseImage1List", houseImage1List);
        map.put("houseBrokerList", houseBrokerList);
        //关注业务后续补充，当前默认返回未关注
        //map.put("isFollow",false); =============================
        //根据用户id与房源id去查
        Boolean isFollowed = false;
        //从Session域中获取userInfo对象
        UserInfo userInfo = (UserInfo) session.getAttribute("user");
        if (userInfo != null) {
            //证明已经登录
            //查看是否关注该房源
            isFollowed = userFollowService.isFollowed(userInfo.getId(), id);
        }
        //将isFollowed放入map中 ================================
        map.put("isFollow",isFollowed);
        return Result.ok(map);
    }
}
