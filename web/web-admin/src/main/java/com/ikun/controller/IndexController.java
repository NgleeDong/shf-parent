package com.ikun.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    /**
     * 后台首页
     * @return
     */
    @RequestMapping("/")
    public String toIndex() {
        return "frame/index";
    }

    /**
     * 后台的主页，用iframe内联框架实现的
     * @return
     */
    @RequestMapping("/main")
    public String toMain() {
        return "frame/main";
    }
}
