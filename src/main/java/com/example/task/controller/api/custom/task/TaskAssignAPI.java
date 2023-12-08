package com.example.task.controller.api.custom.task;

import com.example.task.dto.ResponseService;
import com.example.task.dto.TaskDTO;
import com.example.task.dto.UserDTO;
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

import static com.example.task.dto.ResponseService.success;
import static com.example.task.dto.constant.Pageable.defaultLimit;
import static com.example.task.dto.constant.Pageable.defaultPage;

@RestController
@RequestMapping(value = "/api/task-assign")
@AllArgsConstructor
public class TaskAssignAPI {
    private ITaskService taskService;
    private IUserService userService;

    @GetMapping(value = "/{userId}")
    public ResponseEntity<ResponseService<List<TaskDTO>>> listTaskByUser(
            @PathVariable Long userId,
            @RequestParam(required = false, defaultValue = "" + defaultPage + "") int page,
            @RequestParam(required = false, defaultValue = "" + defaultLimit + "") int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        try {
            UserDTO user = userService.findById(userId).orElseThrow(NullPointerException::new);
            Page<TaskDTO> tasks = taskService.findAllByUsername(pageable, user.getUsername());
            return new ResponseEntity<>(new ResponseService<>("Success", tasks.getContent(), tasks.getTotalPages(), page, limit, "200"), HttpStatus.OK);
        } catch (Exception ex) {
            ResponseService<List<TaskDTO>> responseService = new ResponseService<>();
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping(value = "/setPoint")
    public ResponseEntity<ResponseService<TaskDTO>> setPoint(@RequestBody TaskDTO taskDTO) {
        try {
            int point = fib(taskDTO.getLevelOfDifficulty());
            TaskDTO task = taskService.findById(taskDTO.getId()).orElseThrow(NullPointerException::new);
            task.setPoint(point);
            taskService.update(task);
            return new ResponseEntity<>(new ResponseService<>(task, "200"), HttpStatus.OK);
        } catch (Exception ex) {
            ResponseService<TaskDTO> responseService = new ResponseService<>();
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping()
    public ResponseEntity<ResponseService<TaskDTO>> assignUser(@RequestBody TaskDTO taskDTO) {

        try {
            TaskDTO taskOld = taskService.findById(taskDTO.getId()).orElseThrow(NullPointerException::new);
            taskService.setEmail(taskOld, taskDTO);
            taskService.update(taskDTO);
            success();
        } catch (Exception ex) {
            ResponseService<TaskDTO> responseService = new ResponseService<>();
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    @GetMapping(value = "/list-assign")
    public ResponseEntity<ResponseService<List<TaskDTO>>> listAssign(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "limit", required = false) Integer limit) {
        try {
            String username = SecurityUtils.getPrincipal().getUsername();
            Pageable pageable = PageRequest.of(page - 1, limit);
            Page<TaskDTO> resultPage = taskService.findAllByUsername(pageable, username);
            return new ResponseEntity<>(new ResponseService<>("Success", resultPage.getContent(),
                    resultPage.getTotalPages(), page, limit, "200"), HttpStatus.OK);
        } catch (Exception ex) {
            ResponseService<List<TaskDTO>> responseService = new ResponseService<>();
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/search/{search}")
    public ResponseEntity<ResponseService<List<TaskDTO>>> search(@PathVariable String search,
                                                                 @RequestParam(value = "page", required = false) Integer page,
                                                                 @RequestParam(value = "limit", required = false) Integer limit) {
        try {
            Pageable pageable = PageRequest.of(page - 1, limit);
            Page<TaskDTO> task = taskService.searchTask(pageable, search);
            return new ResponseEntity<>(new ResponseService<>("Success", task.getContent(), task.getTotalPages(), page, limit, "200"), HttpStatus.OK);
        } catch (Exception ex) {
            ResponseService<List<TaskDTO>> responseService = new ResponseService<>();
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
    }

    private int fib(int n) {
        if (n < 2) return 1;
        else return fib(n - 1) + fib(n - 2);
    }
}
