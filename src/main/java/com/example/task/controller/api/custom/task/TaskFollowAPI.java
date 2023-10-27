package com.example.task.controller.api.custom.task;

import com.example.task.controller.api.CommonAPI;
import com.example.task.dto.FollowDTO;
import com.example.task.dto.ResponseService;
import com.example.task.dto.TaskDTO;
import com.example.task.dto.UserDTO;
import com.example.task.service.IFollowService;
import com.example.task.service.ITaskService;
import com.example.task.service.IUserService;
import com.example.task.utils.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/api/task-follow")
@AllArgsConstructor
public class TaskFollowAPI extends CommonAPI {

    private IFollowService followService;
    private ITaskService taskService;
    private IUserService userService;

    @GetMapping(value = "/list")
    public ResponseEntity<ResponseService> listTaskFollow() {
        String username = SecurityUtils.getPrincipal().getUsername();
        UserDTO user = userService.findByUsername(username);
        responseService.setMessage("Success");
        List<TaskDTO> data;
        try {
            data = taskService.findFollowByUserId(user.getId());
        } catch (Exception ex) {
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ResponseService(data, "200"), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<ResponseService> createFollowTask(@RequestBody FollowDTO followDTO) {
        responseService.setMessage("Success");
        try {
            String username = SecurityUtils.getPrincipal().getUsername();
            Long userId = userService.findByUsername(username).getId();
            List<FollowDTO> listFollow = followService.findByUserId(userId);
            for (Long taskId : followDTO.getTaskIds()) {
                for (FollowDTO item : listFollow) {
                    if (Objects.equals(taskId, item.getTaskId())) {
                        continue;
                    }
                    followDTO.setTaskId(taskId);
                    followDTO.setUserId(userId);
                    followService.save(followDTO);
                }
            }
        } catch (Exception ex) {
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(responseService);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<ResponseService> deleteFollow(@RequestBody FollowDTO followDTO) {
        responseService.setMessage("Success");
        try {
            followService.delete(followDTO.getTaskIds());
        } catch (Exception ex) {
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(responseService);
    }
}
