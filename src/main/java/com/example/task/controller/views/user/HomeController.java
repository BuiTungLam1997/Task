package com.example.task.controller.views.user;

import com.example.task.dto.UserDTO;
import com.example.task.service.IUserService;
import com.example.task.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller(value = "homeControllerOfUser")
public class HomeController {

    @Autowired
    private IUserService userService;

    @GetMapping(value = {"/user-home"})
    public ModelAndView homePage() {
        String username = SecurityUtils.getPrincipal().getUsername();
        UserDTO userDTO = userService.findByUsername(username);

        if (userDTO != null) {
            UserDTO user = userDTO;
            user.setListPermission(userService.getListPermission(username));
            ModelAndView mav = new ModelAndView("user/home");
            mav.addObject("model", user);
            return mav;
        }

        return new ModelAndView("user/home");
    }
}
