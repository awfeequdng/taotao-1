package com.taotao.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面跳转controller
 * @author xianzhixianzhixian 20181107
 */
@Controller
@RequestMapping("/page")
public class PageController {

    @RequestMapping("/register")
    public String showRegister(){
        return "register";
    }

    @RequestMapping("/login")
    public String showLogin(String redirect, Model model){
        model.addAttribute("redirect", redirect);
        return "login";
    }
}
