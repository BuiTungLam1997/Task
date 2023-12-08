package com.example.task.controller.api.custom.user;

import com.example.task.dto.ResponseService;
import com.example.task.dto.TaskDTO;
import com.example.task.service.ITaskService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.task.dto.constant.Pageable.defaultLimit;
import static com.example.task.dto.constant.Pageable.defaultPage;

@RestController
@RequestMapping(value = "/api/report/user")
@AllArgsConstructor
public class ReportAPI {

    ITaskService taskService;

    @GetMapping("/{userId}/{period}")
    public ResponseEntity<ResponseService<List<TaskDTO>>> report(@PathVariable Long userId, @PathVariable(required = false) Integer period,
                                                                 @RequestParam(required = false, defaultValue = "" + defaultPage + "") int page,
                                                                 @RequestParam(required = false, defaultValue = "" + defaultLimit + "") int limit) {
        try {
            period = (period == null) ? 1 : period;
            Page<TaskDTO> task = taskService.findByPeriod(userId, period);
            return new ResponseEntity<>(new ResponseService<>("Success", task.getContent(), task.getTotalPages(), page, limit, "200"), HttpStatus.OK);
        } catch (Exception ex) {
            ResponseService<List<TaskDTO>> responseService = new ResponseService<>();
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }

    }
}
