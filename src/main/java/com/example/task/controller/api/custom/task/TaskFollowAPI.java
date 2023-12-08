package com.example.task.controller.api.custom.task;

import com.example.task.dto.FollowDTO;
import com.example.task.dto.ResponseService;
import com.example.task.dto.TaskDTO;
import com.example.task.service.IFollowService;
import com.example.task.service.ITaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.task.dto.ResponseService.success;

@RestController
@RequestMapping(value = "/api/task-follow")
@AllArgsConstructor
public class TaskFollowAPI {

    private IFollowService followService;
    private ITaskService taskService;

    @GetMapping(value = "/list")
    public ResponseEntity<ResponseService<List<TaskDTO>>> listTaskFollow() {
        try {
            List<TaskDTO> data = taskService.findFollowByUserId();
            return new ResponseEntity<>(new ResponseService<>(data, "200"), HttpStatus.OK);
        } catch (Exception ex) {
            ResponseService<List<TaskDTO>> responseService = new ResponseService<>();
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping(value = "/create")
    public ResponseEntity<ResponseService<TaskDTO>> createFollowTask(@RequestBody FollowDTO followDTO) {
        ResponseService<TaskDTO> responseService = new ResponseService<>();
        try {
            followService.save(followDTO);
            success();
        } catch (Exception ex) {
            responseService.setMessage(ex.getMessage());
        }
        return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<ResponseService<TaskDTO>> deleteFollow(@RequestBody FollowDTO followDTO) {
        ResponseService<TaskDTO> responseService = new ResponseService<>();
        try {
            followService.delete(followDTO.getTaskIds());
            return new ResponseEntity<>(new ResponseService<>("Success", "200"), HttpStatus.OK);
        } catch (Exception ex) {
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
    }
}
