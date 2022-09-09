package com.ikun.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.ikun.entity.UserInfo;
import com.ikun.result.Result;
import com.ikun.result.ResultCodeEnum;
import com.ikun.service.UserInfoService;
import com.ikun.util.MD5;
import com.ikun.vo.LoginVo;
import com.ikun.vo.RegisterVo;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/userInfo")
public class UserInfoController {

    @Reference
    private UserInfoService userInfoService;

    //axios.get('/userInfo/sendCode/'+this.registerVo.phone)
    //发送验证码
    @RequestMapping("/sendCode/{phone}")
    public Result sendCode(@PathVariable("phone") String phone, HttpServletRequest request) {
        //设置验证码为 8888
        String code = "8888";
        //把验证码放入Session域中（也可以放redis中，麻烦，此处先不用了）
        //目的：一会从Session域中取出来做验证码的校验
        request.getSession().setAttribute("code", code);
        //将验证码响应到前端
        return Result.ok(code);
    }

    //axios.post('/userInfo/register', this.registerVo)
    //注册
    @RequestMapping("/register")
    public Result register(@RequestBody RegisterVo registerVo, HttpSession session) {
        String phone = registerVo.getPhone();
        String password = registerVo.getPassword();
        String nickName = registerVo.getNickName();
        String code = registerVo.getCode();
        //验空
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password) || StringUtils.isEmpty(nickName) || StringUtils.isEmpty(code)) {
            //返回参数错误的消息
            return Result.build(null, ResultCodeEnum.PARAM_ERROR);
        }
        //从Session域中获取验证码
        String sessionCode = (String) session.getAttribute("code");
        //判断验证码是否正确
        if (!code.equals(sessionCode)) {
            //返回验证码错误的消息
            return Result.build(null, ResultCodeEnum.CODE_ERROR);
        }

        //调用userInfoService中的方法：判断该手机号是否已经注册
        UserInfo userInfo = userInfoService.getUserInfoByPhone(phone);
        if (userInfo != null) {
            //返回手机号已经注册的消息
            return Result.build(null, ResultCodeEnum.PHONE_REGISTER_ERROR);
        }

        //可以注册
        //创建一个UserInfo对象插入到数据库中
        UserInfo userInfo1 = new UserInfo();
        userInfo1.setPhone(phone);
        userInfo1.setNickName(nickName);
        userInfo1.setPassword(MD5.encrypt(password));
        userInfo1.setStatus(1);
        userInfoService.insert(userInfo1);
        return Result.ok();
    }

    //axios.post('/userInfo/login', this.loginVo)
    //登录
    @RequestMapping("/login")
    public Result login(@RequestBody LoginVo loginVo, HttpSession session) {
        //获取手机号和密码
        String phone = loginVo.getPhone();
        String password = loginVo.getPassword();
        //验空
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password)) {
            return Result.build(null, ResultCodeEnum.PARAM_ERROR);
        }
        //根据手机号查询用户信息
        UserInfo userInfo = userInfoService.getUserInfoByPhone(phone);
        if (userInfo == null) {
            //用户不存在
            return Result.build(null, ResultCodeEnum.ACCOUNT_ERROR);
        }
        //验证密码是否正确
        //MD5同样的密码加密之后 密文也是一样的
        if (!MD5.encrypt(password).equals(userInfo.getPassword())) {
            //密码不正确
            return Result.build(null, ResultCodeEnum.PASSWORD_ERROR);
        }
        //判断用户是否被锁定
        if (userInfo.getStatus() == 0) {
            //被锁定
            return Result.build(null, ResultCodeEnum.ACCOUNT_LOCK_ERROR);
        }
        //登录成功，将用户信息放入Session域中
        session.setAttribute("user", userInfo);
        //创建一个map，map中必须存放 nickname的key，值是用户的昵称
        Map<String, String> map = new HashMap<>();
        map.put("nickName", userInfo.getNickName());
        map.put("phone", userInfo.getPhone());
        //响应
        return Result.ok(map);
    }

    //axios.get('/userInfo/logout')
    //登出
    @RequestMapping("/logout")
    public Result logout(HttpSession session) {
        //将session域中的userInfo对象移除
        session.removeAttribute("user");
        return Result.ok();
    }
}
