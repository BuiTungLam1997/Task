package com.example.task.api;

import com.example.task.dto.UserGroupDTO;
import com.example.task.service.IUserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/user-group")
public class UserGroupAPI {
    @Autowired
    private IUserGroupService userGroupService;

    @PostMapping
    public UserGroupDTO createUserGroup(@RequestBody UserGroupDTO userGroupDTO) {
        if (userGroupDTO.getPermissionIds() != null) {
            for (Long item : userGroupDTO.getPermissionIds()) {
                userGroupDTO.setPermissionId(item);
                userGroupService.save(userGroupDTO);
            }
        } else if (userGroupDTO.getIds() != null) {
            for (Long item : userGroupDTO.getIds()) {
                userGroupDTO.setUserId(item);
                userGroupService.save(userGroupDTO);
            }
        }
        return userGroupDTO;
    }

    @PutMapping
    public void updateUserGroup(@RequestBody UserGroupDTO userGroupDTO) {
        userGroupService.update(userGroupDTO);
    }

    @DeleteMapping
    public void deleteUserGroup(@RequestBody UserGroupDTO userGroupDTO) {
        userGroupService.delete(userGroupDTO.getIds());
    }
}
