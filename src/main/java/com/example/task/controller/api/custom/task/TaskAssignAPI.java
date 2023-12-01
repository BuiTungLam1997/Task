package com.example.task.controller.api.custom.task;

import com.example.task.controller.api.CommonAPI;
import com.example.task.dto.EmailDTO;
import com.example.task.dto.ResponseService;
import com.example.task.dto.TaskDTO;
import com.example.task.dto.UserDTO;
import com.example.task.dto.constant.StatusTask;
import com.example.task.service.IEmailService;
import com.example.task.service.ITaskService;
import com.example.task.service.IUserService;
import com.example.task.utils.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.task.dto.constant.Pageable.defaultLimit;
import static com.example.task.dto.constant.Pageable.defaultPage;
import static com.example.task.dto.constant.StatusSent.UNSENT;

@RestController
@RequestMapping(value = "/api/task-assign")
@AllArgsConstructor
public class TaskAssignAPI extends CommonAPI {
    private ITaskService taskService;
    private IEmailService emailService;
    private IUserService userService;

    @GetMapping(value = "/{userId}")
    public ResponseEntity<ResponseService> listTaskByUser(@PathVariable Long userId,
                                                          @RequestParam(required = false, defaultValue = "" + defaultPage + "") int page,
                                                          @RequestParam(required = false, defaultValue = "" + defaultLimit + "") int limit,
                                                          @RequestParam(required = false) String search) {
        responseService.setMessage("Success");
        List<TaskDTO> listTask;
        Pageable pageable = PageRequest.of(page - 1, limit);
        try {
            UserDTO user = userService.findById(userId).orElseThrow(NullPointerException::new);
            Page<TaskDTO> resultPage = taskService.findAllByUsername(pageable, user.getUsername());
            listTask = resultPage.getContent();
            return new ResponseEntity<>(new ResponseService("Success", listTask, resultPage.getTotalPages(), page, limit, "200"), HttpStatus.OK);
        } catch (Exception ex) {
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping(value = "/setPoint")
    public ResponseEntity<ResponseService> setPoint(@RequestBody TaskDTO taskDTO) {
        responseService.setMessage("Success");
        try {
            int point = fib(taskDTO.getLevelOfDifficulty());
            TaskDTO task = taskService.findById(taskDTO.getId()).orElseThrow(NullPointerException::new);
            task.setPoint(point);
            taskService.update(task);
            return new ResponseEntity<>(new ResponseService(taskService.findById(taskDTO.getId()), "200"), HttpStatus.OK);
        } catch (Exception ex) {
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping()
    public ResponseEntity<ResponseService> assignUser(@RequestBody TaskDTO taskDTO) {
        responseService.setMessage("Success");
        try {
            TaskDTO taskOld = taskService.findById(taskDTO.getId()).orElseThrow(NullPointerException::new);
            EmailDTO email = new EmailDTO();
            setEmail(taskOld, taskDTO, email);
            if (taskDTO.getStatus().equals(StatusTask.DONE)) {
                UserDTO user = userService.findByUsername(taskDTO.getPerformer()).orElseThrow(NullPointerException::new);
                int totalPoint = user.getTotalPoint() + taskDTO.getPoint();
                user.setTotalPoint(totalPoint);
                userService.update(user);
            }
            taskService.update(taskDTO);
            return ResponseEntity.ok(responseService);
        } catch (Exception ex) {
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/list-assign")
    public ResponseEntity<ResponseService> listAssign(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "limit", required = false) Integer limit) {
        responseService.setMessage("Success");
        try {
            String username = SecurityUtils.getPrincipal().getUsername();
            Pageable pageable = PageRequest.of(page - 1, limit);
            Page<TaskDTO> resultPage = taskService.findAllByUsername(pageable, username);
            return new ResponseEntity<>(new ResponseService("Success", resultPage.getContent(),
                    resultPage.getTotalPages(), page, limit, "200"), HttpStatus.OK);
        } catch (Exception ex) {
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/search/{search}")
    public ResponseEntity<ResponseService> search(@PathVariable String search,
                                                  @RequestParam(value = "page", required = false) Integer page,
                                                  @RequestParam(value = "limit", required = false) Integer limit) {
        responseService.setMessage("Success");
        try {
            Pageable pageable = PageRequest.of(page - 1, limit);
            Page<TaskDTO> task = taskService.searchTask(pageable, search);
            return new ResponseEntity<>(new ResponseService("Success", task.getContent(), task.getTotalPages(), page, limit, "200"), HttpStatus.OK);
        } catch (Exception ex) {
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
    }

    private String getEmailByUsername(String username) {
        return userService.findByUsername(username).orElseThrow(NullPointerException::new).getEmail();

    }

    private void setEmail(TaskDTO taskOld, TaskDTO taskDTO, EmailDTO email) {
        email.setToEmail(getEmailByUsername(taskDTO.getCreatedBy()));
        saveEmail(taskOld, taskDTO, email);

        List<UserDTO> users = userService.findByFollow(taskDTO.getId());
        for (UserDTO user : users) {
            email.setToEmail(user.getEmail());
            saveEmail(taskOld, taskDTO, email);
        }

        if (taskDTO.getPerformer().equals(taskOld.getPerformer())) {
            email.setToEmail(getEmailByUsername(taskOld.getPerformer()));
            saveEmail(taskOld, taskDTO, email);
        } else {
            email.setToEmail(getEmailByUsername(taskOld.getPerformer()));
            saveEmail(taskOld, taskDTO, email);

            email.setToEmail(getEmailByUsername(taskDTO.getPerformer()));
            saveEmail(taskOld, taskDTO, email);
        }
    }

    private void saveEmail(TaskDTO taskOld, TaskDTO taskDTO, EmailDTO email) {
        StringBuilder title = new StringBuilder("Thông báo thay đổi task có tiêu đề ");
        title.append(taskDTO.getTitle().toUpperCase());
        title.append(" thành ");
        title.append(taskDTO.getTitle().toUpperCase());
        email.setTitle(title.toString());

        StringBuilder content = new StringBuilder("Thay đổi task có nội dung từ ");
        content.append(taskOld.getContent().toUpperCase());
        content.append(" thành ");
        content.append(taskDTO.getContent().toUpperCase());
        content.append(" trang thái từ ");
        content.append(taskOld.getStatus());
        content.append(" thành ");
        content.append(taskDTO.getStatus());
        content.append(". Người thực hiện ").append(taskDTO.getPerformer().toUpperCase());
        email.setContent(content.toString());
        email.setStatusSent(String.valueOf(UNSENT));
        emailService.save(email);
    }

    private int fib(int n) {
        if (n < 2) return 1;
        else return fib(n - 1) + fib(n - 2);
    }
}
