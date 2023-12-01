package com.example.task.controller.api;

import com.example.task.dto.ResponseService;
import com.example.task.dto.UserGroupDTO;
import com.example.task.service.IUserGroupService;
import lombok.AllArgsConstructor;
import org.jboss.weld.context.http.Http;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/user-group")
@AllArgsConstructor
public class UserGroupAPI extends CommonAPI {
    private IUserGroupService userGroupService;

    @PostMapping
    public ResponseEntity<ResponseService> createUserGroup(@RequestBody UserGroupDTO userGroupDTO) {
        responseService.setMessage("Insert success");
        try {
            userGroupService.save(userGroupDTO);
            return ResponseEntity.ok(responseService);
        } catch (Exception ex) {
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping
    public ResponseEntity<ResponseService> updateUserGroup(@RequestBody UserGroupDTO userGroupDTO) {
        responseService.setMessage("Update success");
        try {
            userGroupService.update(userGroupDTO);
            return ResponseEntity.ok(responseService);
        } catch (Exception ex) {
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping
    public ResponseEntity<ResponseService> deleteUserGroup(@RequestBody UserGroupDTO userGroupDTO) {
        responseService.setMessage("Delete success");
        try {
            userGroupService.delete(userGroupDTO.getIds());
            return ResponseEntity.ok(responseService);
        } catch (Exception ex) {
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }

    }
}
