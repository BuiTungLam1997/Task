package com.example.task.controller.user;

import com.example.task.dto.TaskDTO;
import com.example.task.service.ITaskService;
import com.example.task.service.IUserService;
import com.example.task.utils.MessageUtils;
import com.example.task.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class TaskControllerUser {
    @Autowired
    private ITaskService taskService;
    @Autowired
    private MessageUtils messageUtils;
    @Autowired
    private IUserService userService;

    //
    @GetMapping(value = "/user-task-list")
    public ModelAndView newsList(@ModelAttribute("model") TaskDTO taskDTO,
                                 @RequestParam(value = "page", required = false) Integer page,
                                 @RequestParam(value = "limit", required = false) Integer limit,
                                 @RequestParam(value = "message", required = false) String message) {
        ModelAndView mav = new ModelAndView("/user/list");
        String username = SecurityUtils.getPrincipal().getUsername();
        if (page == null || limit == null) {
            page = 1;
            limit = 4;
        }
        Pageable pageable = PageRequest.of(page - 1, limit);
        taskDTO.setPage(page);
        taskDTO.setLimit(limit);
        taskDTO.setListResult(taskService.findAllByUsername(pageable, username));
        taskDTO.setTotalItem(taskService.getTotalItemByUsername(username));
        taskDTO.setTotalPage((int) Math.ceil((double) (taskDTO.getTotalItem()) / taskDTO.getLimit()));
        mav.addObject("model", taskDTO);

        if (message != null) {
            Map<String, String> result = messageUtils.getMessage(message);
            mav.addObject("messageResponse", result.get("message"));
            mav.addObject("alert", result.get("alert"));
        }
        return mav;
    }
}
