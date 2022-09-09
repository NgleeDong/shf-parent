package com.ikun.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.ikun.entity.*;
import com.ikun.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/house")
public class HouseController extends BaseController{

    public static final String SUCCESS_PAGE = "common/successPage";


    @Reference
    private HouseService houseService;

    @Reference
    private CommunityService communityService;

    @Reference
    private DictService dictService;//根据编码查询户型、楼层等

    @Reference
    private HouseImageService houseImageService;

    @Reference
    private HouseBrokerService houseBrokerService;

    @Reference
    private HouseUserService houseUserService;


    //分页及带条件查询的方法
    @RequestMapping
    public String index(Map map, HttpServletRequest request) {
        Map<String, Object> filters = getFilters(request);
        map.put("filters", filters);
        PageInfo<House> pageInfo = houseService.findPage(filters);
        map.put("page", pageInfo);
        //将小区和数字字典中的数据放如Request域
        setRequestAttribute(map);
        return "house/index";
    }

    //去添加房源的页面
    @RequestMapping("/create")
    public String toCreate(Map map) {
        //因为有些东西是需要下拉列表让选择的，所以需要传过去一个map
        //将小区和数字字典中的数据放如Request域
        setRequestAttribute(map);
        return "/house/create";
    }

    //添加房源
    @RequestMapping("/save")
    public String save(House house) {
        houseService.insert(house);
        return SUCCESS_PAGE;
    }

    //去修改页面
    @RequestMapping("/edit/{id}")
    public String goEdit(@PathVariable("id") Long id,Map map) {
        House house = houseService.getById(id);
        map.put("house", house);
        //将小区和数字字典中的数据放如Request域
        setRequestAttribute(map);
        return "house/edit";
    }

    //更新
    @RequestMapping("/update")
    public String update(House house) {
        houseService.update(house);
        return SUCCESS_PAGE;
    }

    //删除
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        houseService.delete(id);
        //重定向
        return "redirect:/house";
    }

    //发布or取消发布
    @RequestMapping("/publish/{id}/{status}")
    public String publish(@PathVariable("id") Long id, @PathVariable("status") Integer status) {
        //注：用POJO入参也可以！
        houseService.publish(id, status);
        //重定向
        return "redirect:/house";
    }

    //去详情页面
    @RequestMapping("/{id}")
    public String toShowDetail(@PathVariable("id") Long id, Map map) {
        //查房子的信息
        House house = houseService.getById(id);
        map.put("house", house);
        //查小区的信息
        Community community = communityService.getById(house.getCommunityId());
        map.put("community", community);
        /*
        但这样放，有些是显示不出来的，如房屋户型，所在楼层...
        因为数据库中放的都是id，我们需要重写houseService和communityService中getById的方法
         */
        //查询房源图片
        List<HouseImage> houseImage1List = houseImageService.getHouseImageByIdAndType(id, 1);
        map.put("houseImage1List", houseImage1List);
        //查询房产图片
        List<HouseImage> houseImage2List = houseImageService.getHouseImageByIdAndType(id, 2);
        map.put("houseImage2List", houseImage2List);
        //查询经纪人
        List<HouseBroker> houseBrokerList = houseBrokerService.getHouseBrokerByHouseId(id);
        map.put("houseBrokerList", houseBrokerList);
        //查询房东
        List<HouseUser> houseUserList = houseUserService.getHouseUserById(id);
        map.put("houseUserList", houseUserList);
        return "house/show";
    }


    //将小区和数字字典中的数据放如Request域
    public void setRequestAttribute(Map map) {
        //获取所有的小区
        List<Community> communityList = communityService.findAll();
        map.put("communityList", communityList);
        //获取所有户型
        List<Dict> houseTypeList = dictService.getListByDictCode("houseType");
        map.put("houseTypeList", houseTypeList);
        //获取楼层
        List<Dict> floorList = dictService.getListByDictCode("floor");
        map.put("floorList", floorList);
        //获取建筑结构
        List<Dict> buildStructureList = dictService.getListByDictCode("buildStructure");
        map.put("buildStructureList", buildStructureList);
        //获取朝向
        List<Dict> directionList = dictService.getListByDictCode("direction");
        map.put("directionList", directionList);
        //获取装修情况
        List<Dict> decorationList = dictService.getListByDictCode("decoration");
        map.put("decorationList", decorationList);
        //获取房屋用途
        List<Dict> houseUseList = dictService.getListByDictCode("houseUse");
        map.put("houseUseList", houseUseList);
    }
}
