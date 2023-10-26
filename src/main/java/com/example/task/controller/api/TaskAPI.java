package com.example.task.controller.api;

import com.example.task.dto.CommentDTO;
import com.example.task.dto.ResponseService;
import com.example.task.dto.TaskDTO;
import com.example.task.dto.constant.StatusTask;
import com.example.task.exception.NullPointException;
import com.example.task.service.ITaskService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.example.task.dto.constant.Pageable.defaultLimit;
import static com.example.task.dto.constant.Pageable.defaultPage;
import static com.example.task.dto.constant.SystemConstant.model;

@RestController
@RequestMapping(value = "/api/task")
@AllArgsConstructor
public class TaskAPI {
    private ITaskService taskService;

    @PostMapping
    public ResponseEntity<ResponseService> createTask(@RequestBody TaskDTO taskDTO) {
        ResponseService responseService = new ResponseService("Insert success");
        try {
            if (taskService.isValidDate(taskDTO)) {
                taskService.save(taskDTO);
            }
        } catch (Exception e) {
            responseService.setMessage(e.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(responseService);
    }

    @PutMapping
    public ResponseEntity<ResponseService> updateTask(@RequestBody TaskDTO taskDTO, @RequestBody CommentDTO commentDTO) {
        ResponseService responseService = new ResponseService("Update success");
        try {
            taskService.update(taskDTO);
        } catch (Exception e) {
            responseService.setMessage(e.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(responseService);
    }

    @PutMapping(value = "/done-task")
    public ResponseEntity<ResponseService> doneTask(@RequestBody TaskDTO taskDTO) {
        ResponseService responseService = new ResponseService("Done task");
        try {
            Optional<TaskDTO> task = taskService.findById(taskDTO.getId());
            if (task.isPresent()) {
                taskDTO = task.get();
                taskDTO.setStatus(StatusTask.DONE);
                taskService.update(taskDTO);
            }
        } catch (Exception e) {
            responseService.setMessage(e.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(responseService);
    }

    @DeleteMapping
    public ResponseEntity<ResponseService> deleteTask(@RequestBody TaskDTO taskDTO) {
        ResponseService responseService = new ResponseService("Delete success");
        try {
            taskService.delete(taskDTO.getIds());
        } catch (Exception e) {
            responseService.setMessage(e.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(responseService);
    }

    @GetMapping(value = "/list")
    public ResponseEntity<ResponseService> listTask(@ModelAttribute TaskDTO model,
                                                    @RequestParam(value = "page", required = false) Integer page,
                                                    @RequestParam(value = "limit", required = false) Integer limit) {
        if (page == null || limit == null) {
            page = defaultPage;
            limit = defaultLimit;
        }
        Pageable pageable = PageRequest.of(page - 1, limit);

        Page<TaskDTO> pageResult = taskService.query(pageable);
        model.setPage(page);
        model.setLimit(limit);

        model.setListResult(pageResult.getContent());
        model.setTotalItem((int) pageResult.getTotalElements());
        model.setTotalPage(pageResult.getTotalPages());
        return new ResponseEntity<>(new ResponseService(model, "200"), HttpStatus.OK);
    }

}
