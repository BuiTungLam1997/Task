package com.example.task.controller.views.user;

import com.example.task.dto.TaskDTO;
import com.example.task.service.ITaskService;
import com.example.task.service.IUserService;
import com.example.task.utils.MessageUtils;
import com.example.task.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.Objects;

import static com.example.task.dto.constant.Pageable.defaultLimit;
import static com.example.task.dto.constant.Pageable.defaultPage;
import static com.example.task.dto.constant.StatusMessage.ALERT;
import static com.example.task.dto.constant.StatusMessage.MESSAGE;
import static com.example.task.dto.constant.SystemConstant.model;

@Controller
public class TaskControllerUser {
    @Autowired
    private ITaskService taskService;
    @Autowired
    private MessageUtils messageUtils;
    @Autowired
    private IUserService userService;

    //
    @GetMapping(value = "/user-task-follow")
    public ModelAndView listTaskFollow() {
        return new ModelAndView("/user/follow");
    }

    @GetMapping(value = "/user-task-list")
    public ModelAndView newsList(@ModelAttribute("model") TaskDTO taskDTO,
                                 @RequestParam(value = "page", required = false) Integer page,
                                 @RequestParam(value = "limit", required = false) Integer limit,
                                 @RequestParam(value = "message", required = false) String message) {
        ModelAndView mav = new ModelAndView("/user/list");
        if (page == null || limit == null) {
            page = 1;
            limit = 4;
        }
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<TaskDTO> resultPage = taskService.query(pageable);
        taskDTO.setPage(page);
        taskDTO.setLimit(limit);
        taskDTO.setListResult(resultPage.getContent());
        taskDTO.setTotalItem((int) resultPage.getTotalElements());
        taskDTO.setTotalPage(resultPage.getTotalPages());
        mav.addObject("model", taskDTO);

        if (message != null) {
            Map<String, String> result = messageUtils.getMessage(message);
            mav.addObject("messageResponse", result.get("message"));
            mav.addObject("alert", result.get("alert"));
        }
        return mav;
    }

    @GetMapping(value = "/user-task-assign")
    public ModelAndView newsListAssign(@ModelAttribute("model") TaskDTO taskDTO,
                                       @RequestParam(value = "page", required = false) Integer page,
                                       @RequestParam(value = "limit", required = false) Integer limit,
                                       @RequestParam(value = "message", required = false) String message) {
        return new ModelAndView("/user/list-assign");
    }

    @GetMapping(value = "/user-search-task")
    public ModelAndView searchTask(@RequestParam(required = false) String search) {
        if (Objects.equals(search, "")) {
            return new ModelAndView("redirect:/user-task-list");
        }
        ModelAndView mav = new ModelAndView("user/search");
        mav.addObject("searchResponse", search);
        return mav;
    }

    private void addMessage(ModelAndView mav, String message) {
        Map<String, String> result = messageUtils.getMessage(message);
        mav.addObject(String.valueOf(MESSAGE), result.get(String.valueOf(MESSAGE)));
        mav.addObject(String.valueOf(ALERT), result.get(String.valueOf(ALERT)));
    }
}
