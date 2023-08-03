package com.example.task.api;

import com.example.task.dto.GroupDTO;
import com.example.task.repository.GroupRepository;
import com.example.task.service.impl.GroupService;
import com.example.task.transformer.GroupTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/group")
public class GroupAPI {
    @Autowired
    private GroupService groupService;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private GroupTransformer groupTransformer;

    @PostMapping
    public GroupDTO createGroup(@RequestBody GroupDTO groupDTO) {
        return groupService.save(groupDTO);
    }

    @PutMapping()
    public GroupDTO updateGroup(@RequestBody GroupDTO groupDTO) {
        return groupService.update(groupDTO);
    }

    @DeleteMapping
    public void deleteGroup(@RequestBody GroupDTO groupDTO) {
        groupService.delete(groupDTO.getIds());
    }
}
