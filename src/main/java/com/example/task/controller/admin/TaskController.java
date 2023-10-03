package com.example.task.controller.admin;

import com.example.task.dto.TaskDTO;
import com.example.task.dto.UserDTO;
import com.example.task.dto.constant.StatusMessage;
import com.example.task.entity.EmailEntity;
import com.example.task.mail.SendEmailService;
import com.example.task.service.IEmailService;
import com.example.task.service.ITaskService;
import com.example.task.service.IUserService;
import com.example.task.service.filter.FilterTask;
import com.example.task.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static com.example.task.dto.constant.Pageable.defaultLimit;
import static com.example.task.dto.constant.Pageable.defaultPage;
import static com.example.task.dto.constant.StatusMessage.*;
import static com.example.task.dto.constant.StatusSent.UNSENT;
import static com.example.task.dto.constant.SystemConstant.model;

@Controller
public class TaskController {
    @Autowired
    private ITaskService taskService;
    @Autowired
    private MessageUtils messageUtils;
    @Autowired
    private IUserService userService;
    @Autowired
    private IEmailService emailService;
    @Autowired
    private SendEmailService sendEmailService;
    @Autowired
    private FilterTask filterTask;

    @GetMapping(value = "/admin-task-list")
    public ModelAndView newsList(@ModelAttribute("model") TaskDTO taskDTO,
                                 @RequestParam(value = "page", required = false) Integer page,
                                 @RequestParam(value = "limit", required = false) Integer limit,
                                 @RequestParam(value = "message", required = false) String message) {

        if (page == null || limit == null) {
            page = defaultPage;
            limit = defaultLimit;
        }
        Pageable pageable = PageRequest.of(page - 1, limit);

        Page<TaskDTO> pageResult = taskService.query(pageable);
        taskDTO.setPage(page);
        taskDTO.setLimit(limit);
        taskDTO.setListResult(pageResult.getContent());
        taskDTO.setTotalItem((int) pageResult.getTotalElements());
        taskDTO.setTotalPage(pageResult.getTotalPages());

        ModelAndView mav = new ModelAndView("/admin/task/list");
        mav.addObject(model, taskDTO);

        if (message != null) {
            addMessage(mav, message);
        }
        return mav;
    }

    @GetMapping(value = "/admin-task-edit")
    public ModelAndView newsEdit(@RequestParam(value = "id", required = false) Long id,
                                 @RequestParam(value = "message", required = false) String message) {
        ModelAndView mav = new ModelAndView("/admin/task/edit");
        Object taskDTO = new TaskDTO();

        taskDTO = (id != null) ? taskService.findById(id) : taskDTO;
        mav.addObject(model, taskDTO);

        if (message != null) {
            addMessage(mav, message);
        }

        return mav;
    }


    private static final String JOB = "/admin/task/giveAJob";

    @GetMapping(value = "/admin-task-giveAJob")
    public ModelAndView giveAJob(@RequestParam(value = "id", required = false) Long id) {
        Optional<TaskDTO> taskDTO = taskService.findById(id);
        ModelAndView mav = new ModelAndView(JOB);
        if (taskDTO.isPresent()) {
            TaskDTO task = taskDTO.get();
            task.setListUsername(userService.findAllUsername());
            mav.addObject(model, task);
        }
        return mav;
    }

    @PostMapping(value = "/admin-task-job")
    public ModelAndView job(@ModelAttribute("model") TaskDTO taskDTO) {
        ModelAndView mav = new ModelAndView();

        if (!checkUsername(taskDTO.getPerformer())) {
            String messageResponse = "Tên người dùng không tồn tại ,kiểm tra lại";
            setMessage(mav, String.valueOf(StatusMessage.ERROR), messageResponse);
            mav.setViewName(JOB);
            return mav;
        }
        Optional<TaskDTO> taskDTO1 = taskService.findById(taskDTO.getId());
        if (!taskDTO1.isPresent()) {
            return mav;
        }
        TaskDTO task = taskDTO1.get();
        task.setPerformer(taskDTO.getPerformer());
        taskService.update(task);
        String messageResponse = "Thao tác thành công ,hệ thống sẽ gửi email đến nhân viên thực hiên.";
        setMessage(mav, String.valueOf(StatusMessage.SUCCESS), messageResponse);

        saveEmail(taskDTO);

        mav.setViewName(JOB);
        return mav;
    }

    private void saveEmail(TaskDTO taskDTO) {
        Optional<UserDTO> userDTO = userService.findByUsername(taskDTO.getPerformer());
        String to = userDTO.map(UserDTO::getEmail).orElseGet(() -> userService.getMailDefault());
        EmailEntity emailEntity = new EmailEntity();
        emailEntity.setToEmail(to);
        emailEntity.setTitle(taskDTO.getTitle());
        emailEntity.setContent(taskDTO.getContent());
        emailEntity.setStatusSent(String.valueOf(UNSENT));
        emailService.save(emailEntity);
    }

    @GetMapping(value = "/admin-search-task")
    public ModelAndView searchTask(@RequestParam(value = "search", required = false) String search,
                                   @RequestParam(value = "searchTws", required = false) String searchTws,
                                   @RequestParam(value = "page", required = false) Integer page,
                                   @RequestParam(value = "limit", required = false) Integer limit,
                                   @RequestParam(value = "message", required = false) String message) {
        if (Objects.equals(search, "")) {
            return new ModelAndView("redirect:/admin-task-list");
        }

        TaskDTO taskDTO = new TaskDTO();
        if (page == null || limit == null) {
            page = defaultPage;
            limit = defaultLimit;
        }
        Pageable pageable = PageRequest.of(page - 1, limit);
        if (search == null) {
            search = searchTws;
        }

        Page<TaskDTO> pageResult = taskService.queryExample(pageable, search);
        taskDTO.setPage(page);
        taskDTO.setLimit(limit);
        taskDTO.setTotalPage(pageResult.getTotalPages());
        taskDTO.setTotalItem((int) pageResult.getTotalElements());
        taskDTO.setSearchResponse(search);
        taskDTO.setListResult(pageResult.getContent());

        ModelAndView mav = new ModelAndView("/admin/task/search");
        if (message != null) {
            addMessage(mav, message);
        }

        mav.addObject("model", taskDTO);
        return mav;
    }

    private boolean checkUsername(String username) {
        return userService.isExist(username);
    }

    private void setMessage(ModelAndView mav, String message, String messageResponse) {
        if (message.equals(String.valueOf(SUCCESS))) {
            String alert = "success";
            mav.addObject(String.valueOf(MESSAGE), messageResponse);
            mav.addObject(String.valueOf(ALERT), alert);
        } else if (message.equals(String.valueOf(ERROR))) {
            String alert = "danger";
            mav.addObject(String.valueOf(MESSAGE), messageResponse);
            mav.addObject(String.valueOf(ALERT), alert);
        }
    }

    private void addMessage(ModelAndView mav, String message) {
        Map<String, String> result = messageUtils.getMessage(message);
        mav.addObject(String.valueOf(MESSAGE), result.get(String.valueOf(MESSAGE)));
        mav.addObject(String.valueOf(ALERT), result.get(String.valueOf(ALERT)));
    }
}
