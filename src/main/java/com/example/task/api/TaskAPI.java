package com.example.task.api;

import com.example.task.Output.ResponseList;
import com.example.task.dto.TaskDTO;
import com.example.task.service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//(value = "manager/api/task")
@RestController(value = "apiOfTask")
@RequestMapping
public class TaskAPI {
    @Autowired
    private ITaskService taskService;

    @GetMapping(value = "/task")
    public ResponseList<TaskDTO> findAll() {
        ResponseList result = new ResponseList();
        result.setStatus("OK");
        result.setMessage("Find all success");
        result.setData(taskService.findAll());
        return result;
    }

    @PostMapping
    public TaskDTO createTask(@RequestBody TaskDTO taskDTO) {
        return taskDTO;
    }

    @PutMapping(value = "/{id}")
    public TaskDTO updateTask(@RequestBody TaskDTO taskDTO) {
        return taskDTO;
    }

    @DeleteMapping
    public void deleteTask(@RequestBody Long[] ids) {

    }
}
