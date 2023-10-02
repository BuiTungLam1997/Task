package com.example.task.api;

import com.example.task.dto.TaskDTO;
import com.example.task.service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//(value = "manager/api/task")
@RestController
@RequestMapping(value = "/api/task")
public class TaskAPI {
    @Autowired
    private ITaskService taskService;

    @PostMapping
    public TaskDTO createTask(@RequestBody TaskDTO taskDTO) {
        if (taskService.isValidDate(taskDTO)) {
            return taskService.save(taskDTO);
        }
        return null;
    }

    @PutMapping
    public TaskDTO updateTask(@RequestBody TaskDTO taskDTO) {
        //luu sang 1 cai db khac de tranh mat du lieu
        return taskService.update(taskDTO);
    }

    @DeleteMapping
    public void deleteTask(@RequestBody TaskDTO taskDTO) {
        taskService.delete(taskDTO.getIds());
    }
}
