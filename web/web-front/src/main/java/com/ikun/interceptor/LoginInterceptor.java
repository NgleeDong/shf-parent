package com.ikun.interceptor;

import com.ikun.entity.UserInfo;
import com.ikun.result.Result;
import com.ikun.result.ResultCodeEnum;
import com.ikun.util.WebUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取session域中的userInfo对象
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("user");
        if (userInfo == null) {
            //证明还没有登录
            Result<String> result = Result.build("还没有登录", ResultCodeEnum.LOGIN_AUTH);
            //将Result对象响应到前端
            WebUtil.writeJSON(response, result); //用了工具类
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
