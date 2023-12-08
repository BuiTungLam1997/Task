package com.example.task.controller.api;

import com.example.task.dto.GroupDTO;
import com.example.task.dto.PermissionDTO;
import com.example.task.dto.ResponseService;
import com.example.task.service.IPermissionService;
import com.example.task.service.impl.GroupService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.task.dto.ResponseService.success;

@RestController
@RequestMapping(value = "/api/group")
@AllArgsConstructor
public class GroupAPI {

    private GroupService groupService;
    private IPermissionService permissionService;

    @GetMapping(value = "/list")
    public ResponseEntity<ResponseService<List<GroupDTO>>> listGroup() {
        return new ResponseEntity<>(new ResponseService<>(groupService.findAll(), "200"), HttpStatus.OK);
    }

    @GetMapping(path = "/list-permission/{groupId}")
    public ResponseEntity<ResponseService<List<PermissionDTO>>> listPermission(@PathVariable Long groupId) {
        List<PermissionDTO> data = permissionService.findByGroupId(groupId);
        return new ResponseEntity<>(new ResponseService<>(data, "200"), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseService<GroupDTO>> createGroup(@RequestBody GroupDTO groupDTO) {
        try {
            groupService.save(groupDTO);
            success();
        } catch (Exception ex) {
            ResponseService<GroupDTO> responseService = new ResponseService<>();
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    @PutMapping()
    public ResponseEntity<ResponseService<GroupDTO>> updateGroup(@RequestBody GroupDTO groupDTO) {
        try {
            groupService.update(groupDTO);
            success();
        } catch (Exception ex) {
            ResponseService<GroupDTO> responseService = new ResponseService<>();
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    @DeleteMapping
    public ResponseEntity<ResponseService<GroupDTO>> deleteGroup(@RequestBody GroupDTO groupDTO) {
        try {
            groupService.delete(groupDTO.getIds());
            success();
        } catch (Exception ex) {
            ResponseService<GroupDTO> responseService = new ResponseService<>();
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
        return null;
    }
}
