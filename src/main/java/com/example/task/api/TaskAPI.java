package com.example.task.api;

import com.example.task.dto.TaskDTO;
import com.example.task.service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//(value = "manager/api/task")
@RestController(value = "apiOfTask")
@RequestMapping(value = "/api/task")
public class TaskAPI {
    @Autowired
    private ITaskService taskService;

    @PostMapping
    public TaskDTO createTask(@RequestBody TaskDTO taskDTO) {
        return taskService.save(taskDTO);
    }

    @PutMapping
    public TaskDTO updateTask(@RequestBody TaskDTO taskDTO) {
        //luu sang 1 cai db khac de tranh mat du lieu
        return taskService.update(taskDTO);
    }

    @DeleteMapping
    public void deleteTask(@RequestBody Long[] ids) {

    }
}
