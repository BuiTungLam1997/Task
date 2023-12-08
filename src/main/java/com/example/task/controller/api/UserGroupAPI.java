package com.example.task.controller.api;

import com.example.task.dto.ResponseService;
import com.example.task.dto.UserGroupDTO;
import com.example.task.service.IUserGroupService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.task.dto.ResponseService.success;

@RestController
@RequestMapping(value = "api/user-group")
@AllArgsConstructor
public class UserGroupAPI {
    private IUserGroupService userGroupService;

    @PostMapping
    public ResponseEntity<ResponseService<UserGroupDTO>> createUserGroup(@RequestBody UserGroupDTO userGroupDTO) {

        try {
            userGroupService.save(userGroupDTO);
            success();
        } catch (Exception ex) {
            ResponseService<UserGroupDTO> responseService = new ResponseService<>();
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    @PutMapping
    public ResponseEntity<ResponseService<UserGroupDTO>> updateUserGroup(@RequestBody UserGroupDTO userGroupDTO) {
        try {
            userGroupService.update(userGroupDTO);
            success();
        } catch (Exception ex) {
            ResponseService<UserGroupDTO> responseService = new ResponseService<>();
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    @DeleteMapping
    public ResponseEntity<ResponseService<UserGroupDTO>> deleteUserGroup(@RequestBody UserGroupDTO userGroupDTO) {
        try {
            userGroupService.delete(userGroupDTO.getIds());
            success();
        } catch (Exception ex) {
            ResponseService<UserGroupDTO> responseService = new ResponseService<>();
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
        return null;
    }
}
