package com.example.task.controller.admin;

import com.example.task.dto.TaskDTO;
import com.example.task.service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller(value = "homeControllerOfTask")
public class TaskController {
    @Autowired
    private ITaskService taskService;

    //
    @RequestMapping(value = "/admin-task-list", method = RequestMethod.GET)
    public ModelAndView newsList(@ModelAttribute("model") TaskDTO taskDTO) {
        ModelAndView mav = new ModelAndView("admin/task/list");
        taskDTO.setListResult( taskService.findAll());
        try {
            mav.addObject("model",taskDTO);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return mav;
    }

    @RequestMapping(value = "/admin-task-edit", method = RequestMethod.GET)
    public ModelAndView newsEdit() {
        ModelAndView mav = new ModelAndView("admin/task/edit");
        return mav;
    }
}
