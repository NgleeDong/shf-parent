package com.ikun.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ikun.entity.HouseImage;
import com.ikun.result.Result;
import com.ikun.service.HouseImageService;
import com.ikun.util.QiniuUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/houseImage")
public class HouseImageController {

    @Reference
    private HouseImageService houseImageService;

    //去上传图片的页面
    @RequestMapping("/uploadShow/{houseId}/{type}")
    public String toupload(@PathVariable("houseId") Long houseId,
                           @PathVariable("type") Integer type,
                           Map map) {
        map.put("houseId", houseId);
        map.put("type", type);
        return "house/upload";
    }

    //上传图片
    @ResponseBody
    @RequestMapping("/upload/{houseId}/{type}")
    public Result upload(@PathVariable("houseId") Long houseId,
                         @PathVariable("type") Integer type,
                         @RequestParam("file") MultipartFile[] files//file相当于表单中的name属性值
                         ) {
        try {
            if (files != null && files.length > 0) {
                for (MultipartFile file : files) {
                    //获取字节数组
                    byte[] bytes = file.getBytes();
                    //获取图片的名字（真实的名字）
                    String filename = file.getOriginalFilename();
                    //通过UUID随机生成一个字符串
                    String newFileName = UUID.randomUUID().toString();
                    //通过Qiniu工具类上传图片到七牛云
                    QiniuUtil.upload2Qiniu(bytes, newFileName);
                    //创建HouseImage对象
                    HouseImage houseImage = new HouseImage();
                    houseImage.setHouseId(houseId);
                    houseImage.setType(type);
                    houseImage.setImageName(filename);
                    //设置图片路径
                    //路径的组成：http://七牛云的域名/随机生成的文件新名字
                    houseImage.setImageUrl("http://rhs87z6s9.hn-bkt.clouddn.com/" + newFileName);
                    //存入数据库中
                    houseImageService.insert(houseImage);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.ok();
    }

    //删除房源或房产图片
    @RequestMapping("/delete/{houseId}/{id}") //houseId重定向用的
    public String delete(@PathVariable("houseId") Long houseId,
                         @PathVariable("id") Long id) {
        houseImageService.delete(id); //七牛云的就不删除了
        //去房源详情界面
        return "redirect:/house/" + houseId;
    }

}
