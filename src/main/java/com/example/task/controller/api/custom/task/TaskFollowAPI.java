package com.example.task.controller.api.custom.task;

import com.example.task.controller.api.CommonAPI;
import com.example.task.dto.FollowDTO;
import com.example.task.dto.ResponseService;
import com.example.task.dto.TaskDTO;
import com.example.task.service.IFollowService;
import com.example.task.service.ITaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/api/task-follow")
@AllArgsConstructor
public class TaskFollowAPI extends CommonAPI {

    private IFollowService followService;
    private ITaskService taskService;

    @GetMapping(value = "/list")
    public ResponseEntity<ResponseService> listTaskFollow() {
        responseService.setMessage("Success");
        List<TaskDTO> data;
        try {
            data = taskService.findFollowByUserId();
            return new ResponseEntity<>(new ResponseService(data, "200"), HttpStatus.OK);
        } catch (Exception ex) {
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping(value = "/create")
    public ResponseEntity<ResponseService> createFollowTask(@RequestBody FollowDTO followDTO) {
        responseService.setMessage("Success");
        try {
            followService.save(followDTO);
            return ResponseEntity.ok(responseService);
        } catch (Exception ex) {
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<ResponseService> deleteFollow(@RequestBody FollowDTO followDTO) {
        responseService.setMessage("Success");
        try {
            followService.delete(followDTO.getTaskIds());
            return new ResponseEntity<>(new ResponseService("Success", "200"), HttpStatus.OK);
        } catch (Exception ex) {
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
    }
}
