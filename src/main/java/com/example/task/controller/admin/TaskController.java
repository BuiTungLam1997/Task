package com.example.task.controller.admin;

import com.example.task.dto.TaskDTO;
import com.example.task.service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller(value = "homeControllerOfTask")
public class TaskController {
    @Autowired
    private ITaskService taskService;

    //
    @RequestMapping(value = "/admin-task-list", method = RequestMethod.GET)
    public ModelAndView newsList(@ModelAttribute("model") TaskDTO taskDTO,
                                 @RequestParam("page") int page,
                                 @RequestParam("limit") int limit) {
        ModelAndView mav = new ModelAndView("admin/task/list");

        Pageable pageable = PageRequest.of(page -1 , limit);
        taskDTO.setPage(page);
        taskDTO.setLimit(limit);
        taskDTO.setListResult(taskService.findAll(pageable));
        taskDTO.setTotalItem(taskService.getTotalItem());
        taskDTO.setTotalPage((int) Math.ceil((double) (taskDTO.getTotalItem()) / taskDTO.getLimit()));
        mav.addObject("model", taskDTO);
        return mav;
    }

    @RequestMapping(value = "/admin-task-edit", method = RequestMethod.GET)
    public ModelAndView newsEdit() {
        ModelAndView mav = new ModelAndView("admin/task/edit");
        return mav;
    }
}
