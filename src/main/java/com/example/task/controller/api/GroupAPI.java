package com.example.task.controller.api;

import com.example.task.dto.GroupDTO;
import com.example.task.dto.PermissionDTO;
import com.example.task.dto.ResponseService;
import com.example.task.repository.GroupRepository;
import com.example.task.service.IPermissionService;
import com.example.task.service.impl.GroupService;
import com.example.task.transformer.GroupTransformer;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/group")
@AllArgsConstructor
public class GroupAPI extends CommonAPI {

    private GroupService groupService;
    private IPermissionService permissionService;

    @GetMapping(value = "/list")
    public ResponseEntity<ResponseService> listGroup() {
        return new ResponseEntity<>(new ResponseService(groupService.findAll(), "200"), HttpStatus.OK);
    }

    @GetMapping(path = "/list-permission/{groupId}")
    public ResponseEntity<ResponseService> listPermission(@PathVariable Long groupId) {
        List<PermissionDTO> data = permissionService.findByGroupId(groupId);
        return new ResponseEntity<>(new ResponseService(data, "200"), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseService> createGroup(@RequestBody GroupDTO groupDTO) {
        responseService.setMessage("Insert succsess");
        try {
            groupService.save(groupDTO);
        } catch (Exception ex) {
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(responseService);
    }

    @PutMapping()
    public ResponseEntity<ResponseService> updateGroup(@RequestBody GroupDTO groupDTO) {
        responseService.setMessage("Update success");
        try {
            groupService.update(groupDTO);
        } catch (Exception ex) {
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(responseService);
    }

    @DeleteMapping
    public ResponseEntity<ResponseService> deleteGroup(@RequestBody GroupDTO groupDTO) {
        responseService.setMessage("Delete success");
        try {
            groupService.delete(groupDTO.getIds());
        } catch (Exception ex) {
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(responseService);
    }
}
