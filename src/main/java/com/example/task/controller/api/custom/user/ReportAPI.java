package com.example.task.controller.api.custom.user;

import com.example.task.controller.api.CommonAPI;
import com.example.task.dto.ResponseService;
import com.example.task.dto.TaskDTO;
import com.example.task.service.ITaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.task.dto.constant.Pageable.defaultLimit;
import static com.example.task.dto.constant.Pageable.defaultPage;

@RestController
@RequestMapping(value = "/api/report/user")
@AllArgsConstructor
public class ReportAPI extends CommonAPI {

    ITaskService taskService;

    @GetMapping("/{userId}/{period}")
    public ResponseEntity<ResponseService> report(@PathVariable Long userId, @PathVariable(required = false) Integer period,
                                                  @RequestParam(required = false, defaultValue = "" + defaultPage + "") int page,
                                                  @RequestParam(required = false, defaultValue = "" + defaultLimit + "") int limit) {
        List<TaskDTO> task;
        String message = "Success";
        int totalPage;
        try {
            period = (period == null) ? 1 : period;
            task = taskService.findByPeriod(userId, period);
            totalPage = ((int) Math.ceil((double) taskService.totalItemByPeriod(userId, period) / limit));
        } catch (Exception ex) {
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ResponseService(message, task, totalPage, page, limit, "200"), HttpStatus.OK);
    }
}
