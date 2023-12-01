package com.example.task.controller.views.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller(value = "homeControllerOfWeb")
public class HomeController {
    @RequestMapping(value = {"/trang-chu"}, method = RequestMethod.GET)
    public ModelAndView homePage() {
        return new ModelAndView("/web/home");
    }

    @RequestMapping(value = {"/dang-nhap"}, method = RequestMethod.GET)
    public ModelAndView Login() {
        return new ModelAndView("login");
    }

    @RequestMapping(value = {"/logout"}, method = RequestMethod.GET)
    public ModelAndView Logout() {
        return new ModelAndView("redirect:/trang-chu");
    }

    @RequestMapping(value = {"/accessDenied"}, method = RequestMethod.GET)
    public ModelAndView accessDenied() {
        return new ModelAndView("redirect:/dang-nhap?accessDenied");
    }
}
