package com.example.task.controller.views.admin;

import com.example.task.dto.UserDTO;
import com.example.task.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

import static com.example.task.dto.constant.SystemConstant.model;

@Controller
@AllArgsConstructor
public class ReportController {
    private IUserService userService;
    @GetMapping(value = "/admin-report-user")
    public ModelAndView reportUser(@RequestParam(required = false) Long id) {
        try {
            UserDTO user = (id != null) ? userService.findById(id).orElseThrow(NullPointerException::new) : null;
            ModelAndView mav = new ModelAndView("/admin/user/report");
            mav.addObject(model, user);
            return mav;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
