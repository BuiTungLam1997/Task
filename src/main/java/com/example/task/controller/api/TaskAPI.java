package com.example.task.controller.api;

import com.example.task.dto.ResponseService;
import com.example.task.dto.TaskDTO;
import com.example.task.dto.UserDTO;
import com.example.task.dto.constant.StatusTask;
import com.example.task.service.ITaskService;
import com.example.task.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.example.task.dto.ResponseService.success;
import static com.example.task.dto.constant.Pageable.defaultLimit;
import static com.example.task.dto.constant.Pageable.defaultPage;

@RestController
@RequestMapping(value = "/api/task")
@AllArgsConstructor
public class TaskAPI {
    private ITaskService taskService;
    private IUserService userService;


    @PostMapping
    public ResponseEntity<ResponseService<TaskDTO>> createTask(@RequestBody TaskDTO taskDTO) {
        ResponseService<TaskDTO> responseService = new ResponseService<>();
        responseService.setMessage("Insert success");
        try {
            if (taskService.isValidDate(taskDTO)) {
                taskService.save(taskDTO);
            }
            return ResponseEntity.ok(responseService);
        } catch (Exception e) {
            responseService.setMessage(e.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping
    public ResponseEntity<ResponseService<TaskDTO>> updateTask(@RequestBody TaskDTO taskDTO) {
        ResponseService<TaskDTO> responseService = new ResponseService<>();
        responseService.setMessage("Update success");
        try {
            taskService.update(taskDTO);
            return ResponseEntity.ok(responseService);
        } catch (Exception e) {
            responseService.setMessage(e.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping(value = "/done-task")
    public ResponseEntity<ResponseService<TaskDTO>> doneTask(@RequestBody TaskDTO taskDTO) {
        ResponseService<TaskDTO> responseService = new ResponseService<>();
        responseService.setMessage("Done task");
        try {
            TaskDTO task = taskService.findById(taskDTO.getId()).orElseThrow(NullPointerException::new);
            task.setStatus(StatusTask.DONE);
            taskService.update(task);
            return ResponseEntity.ok(responseService);
        } catch (Exception e) {
            responseService.setMessage(e.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping
    public ResponseEntity<ResponseService<TaskDTO>> deleteTask(@RequestBody TaskDTO taskDTO) {
        ResponseService<TaskDTO> responseService = new ResponseService<>();
        try {
            taskService.delete(taskDTO.getIds());
            success();
        } catch (Exception e) {
            responseService.setMessage(e.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    @GetMapping(value = "/list")
    public ResponseEntity<ResponseService<List<TaskDTO>>> listTask(@RequestParam(required = false, defaultValue = "" + defaultPage + "") Integer page,
                                                                   @RequestParam(required = false, defaultValue = "" + defaultLimit + "") Integer limit) {
        ResponseService<List<TaskDTO>> responseService = new ResponseService<>();
        try {
            Pageable pageable = PageRequest.of(page - 1, limit);
            Page<TaskDTO> tasks = taskService.query(pageable);
            return new ResponseEntity<>(new ResponseService<>("Success", tasks.getContent(),
                    tasks.getTotalPages(), page, limit, "200"), HttpStatus.OK);
        } catch (Exception ex) {
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/edit/{id}")
    public ResponseEntity<ResponseService<TaskDTO>> edit(@PathVariable Long id) {
        ResponseService<TaskDTO> responseService = new ResponseService<>();
        responseService.setMessage("Success");
        try {
            Optional<TaskDTO> taskDTO;
            taskDTO = (id != null) ? taskService.findById(id) : Optional.empty();
            TaskDTO task = taskDTO.orElseThrow(NullPointerException::new);
            task.setLevelOfDifficulty((task.getPoint() != null) ? unFibonaci(task.getPoint()) : 0);
            return new ResponseEntity<>(new ResponseService<>(task, "200"), HttpStatus.OK);
        } catch (Exception ex) {
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/search/{search}")
    public ResponseEntity<ResponseService<List<TaskDTO>>> search(@PathVariable String search,
                                                                 @RequestParam(required = false, defaultValue = "" + defaultPage + "") Integer page,
                                                                 @RequestParam(required = false, defaultValue = "" + defaultLimit + "") Integer limit) {
        ResponseService<List<TaskDTO>> responseService = new ResponseService<>();
        responseService.setMessage("Success");
        try {
            Pageable pageable = PageRequest.of(page - 1, limit);
            Page<TaskDTO> task = taskService.searchTask(pageable, search);
            responseService.setStatus("200");
            if (task.getContent().size() == 0) {
                responseService.setMessage("Không tìm thấy phần tử nào ,mời bạn nhập lại");
                responseService.setStatus("400");
            }
            return new ResponseEntity<>(new ResponseService<>(responseService.getMessage(), task.getContent(),
                    task.getTotalPages(), page, limit, responseService.getStatus()), HttpStatus.OK);
        } catch (Exception ex) {
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/get/{id}")
    public ResponseEntity<ResponseService<TaskDTO>> getOne(@PathVariable Long id) {
        try {
            TaskDTO task = taskService.findById(id).orElseThrow(NullPointerException::new);
            return new ResponseEntity<>(new ResponseService<>("Success", task, "200"), HttpStatus.OK);
        } catch (Exception ex) {
            ResponseService<TaskDTO> responseService = new ResponseService<>();
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/giveAJob")
    public ResponseEntity<ResponseService<TaskDTO>> giveAJob(@RequestBody TaskDTO taskDTO) {
        ResponseService<TaskDTO> responseService = new ResponseService<>();
        responseService.setMessage("Success");
        responseService.setStatus("200");
        try {
            Optional<UserDTO> user = userService.findByUsername(taskDTO.getPerformer());
            if (user.isPresent()) {
                TaskDTO task = taskService.findById(taskDTO.getId()).orElseThrow(NullPointerException::new);
                task.setPerformer(taskDTO.getPerformer());
                task.setStatus(StatusTask.WORKING);
                taskService.update(task);
                responseService.setMessage("Thao tác thành công, hệ thống sẽ gửi email đến nhân viên thực hiên");
                taskService.saveEmail(task);
                return new ResponseEntity<>(new ResponseService<>(responseService.getMessage(),
                        responseService.getStatus()), HttpStatus.OK);
            }
            responseService.setMessage("Tên nhân viên không tồn tại ,kiểm tra lại ");
            responseService.setStatus("400");
            return new ResponseEntity<>(new ResponseService<>(responseService.getMessage(),
                    responseService.getStatus()), HttpStatus.OK);
        } catch (Exception ex) {
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
    }

    private int unFibonaci(int point) {
        if (point <= 1) return 1;
        else {
            int i = 2;
            while (fib(i) != point) {
                i++;
            }
            return i;
        }
    }

    private int fib(int n) {
        if (n < 2) return 1;
        else return fib(n - 1) + fib(n - 2);
    }
}
