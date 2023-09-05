package com.example.task.controller.admin;

import com.example.task.dto.TaskDTO;
import com.example.task.dto.UserDTO;
import com.example.task.mail.emailService;
import com.example.task.service.ITaskService;
import com.example.task.service.IUserService;
import com.example.task.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.Optional;

@Controller
public class TaskController {
    @Autowired
    private ITaskService taskService;
    @Autowired
    private MessageUtils messageUtils;
    @Autowired
    private IUserService userService;
    @Autowired
    private emailService emailService;

    //
    @GetMapping(value = "/admin-task-list")
    public ModelAndView newsList(@ModelAttribute("model") TaskDTO taskDTO,
                                 @RequestParam("page") int page,
                                 @RequestParam("limit") int limit,
                                 @RequestParam(value = "message", required = false) String message) {
        ModelAndView mav = new ModelAndView("/admin/task/list");

        Pageable pageable = PageRequest.of(page - 1, limit);
        taskDTO.setPage(page);
        taskDTO.setLimit(limit);
        taskDTO.setListResult(taskService.findAll(pageable));
        taskDTO.setTotalItem(taskService.getTotalItem());
        taskDTO.setTotalPage((int) Math.ceil((double) (taskDTO.getTotalItem()) / taskDTO.getLimit()));
        mav.addObject("model", taskDTO);

        if (message != null) {
            Map<String, String> result = messageUtils.getMessage(message);
            mav.addObject("messageResponse", result.get("message"));
            mav.addObject("alert", result.get("alert"));
        }
        return mav;
    }

    @GetMapping(value = "/admin-task-edit")
    public ModelAndView newsEdit(@RequestParam(value = "id", required = false) Long id) {

        ModelAndView mav = new ModelAndView("/admin/task/edit");
        TaskDTO taskDTO = new TaskDTO();
        if (id != null) {
            taskDTO = taskService.findById(id);
        }
        mav.addObject("model", taskDTO);
        return mav;
    }

    @GetMapping(value = "/admin-task-giveAJob")
    public ModelAndView giveAJob(@RequestParam(value = "id", required = false) Long id) {
        ModelAndView mav = new ModelAndView("/admin/task/giveAJob");
        TaskDTO taskDTO = taskService.findById(id);
        mav.addObject("model", taskDTO);
        return mav;
    }

    @PostMapping(value = "/admin-task-job")
    public ModelAndView job(@ModelAttribute("model") TaskDTO taskDTO) {
        ModelAndView mav = new ModelAndView();
        String str = "";
        if (!checkUsername(taskDTO.getPerformer())) {
            setMessage(mav, "error");
            str = "/admin/task/giveAJob";
            mav.setViewName(str);
            return mav;
        }
        try {
            TaskDTO task = taskService.findById(taskDTO.getId());
            task.setPerformer(taskDTO.getPerformer());
            taskService.update(task);
            str = "/admin/task/giveAJob";
            setMessage(mav, "success");

            sendMail(taskDTO);

            mav.setViewName(str);
            return mav;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }

    private boolean checkUsername(String username) {
        return userService.isExist(username);
    }

    private void setMessage(ModelAndView mav, String message) {
        if (message.equals("success")) {
            message = "Thao tác thành công";
            String alert = "success";
            mav.addObject("message", message);
            mav.addObject("alert", alert);
        } else if (message.equals("error")) {
            message = "Tên người dùng không tồn tại ,kiểm tra lại";
            String alert = "danger";
            mav.addObject("message", message);
            mav.addObject("alert", alert);
        }
    }

    private void sendMail(TaskDTO taskDTO) {
        Optional<UserDTO> userDTO = userService.findByUsername(taskDTO.getPerformer());
        String to = userDTO.get().getEmail();
        if (to == null){
            to = "admin@gmail.com";
        }
        String title = taskDTO.getTitle();
        String content = taskDTO.getContent();
        emailService.sendMail(to, title, content);
    }
}
