package com.example.task.api;

import com.example.task.Output.ResponseList;
import com.example.task.dto.TaskDTO;
import com.example.task.service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//(value = "manager/api/task")
@RestController(value = "apiOfTask")
@RequestMapping(path = "/api-task")
public class TaskAPI {
    @Autowired
    private ITaskService taskService;

    @PostMapping()
    public TaskDTO createTask(@RequestBody TaskDTO taskDTO) {
        return taskService.save(taskDTO);
    }

    @PutMapping()
    public TaskDTO updateTask(@RequestBody TaskDTO taskDTO) {
        //luu sang 1 cai db khac de tranh mat du lieu
        return taskService.save(taskDTO);
    }

    @DeleteMapping()
    public void deleteTask(@RequestBody Long[] ids) {

    }
}
