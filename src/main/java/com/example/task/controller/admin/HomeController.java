package com.example.task.controller.admin;

import com.example.task.dto.UserDTO;
import com.example.task.service.IUserService;
import com.example.task.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;


@Controller(value = "homeControllerOfAdmin")
public class HomeController {

    @Autowired
    private IUserService userService;

    @GetMapping(value = {"/admin-home"})
    public ModelAndView homePage() {
        String username = SecurityUtils.getPrincipal().getUsername();
        Optional<UserDTO> userDTO = userService.findByUsername(username);

        if (userDTO.isPresent()) {
            UserDTO user = userDTO.get();
            user.setListPermission(userService.getListPermission(username));
            ModelAndView mav = new ModelAndView("admin/home");
            mav.addObject("model", user);
            return mav;
        }

        return new ModelAndView("admin/home");
    }
}
