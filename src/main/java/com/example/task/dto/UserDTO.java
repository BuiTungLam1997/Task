package com.example.task.dto;

import com.example.task.dto.constant.StatusUser;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserDTO extends BaseDTO<UserDTO> {

    private String username;
    private String password;
    private String fullName;
    private StatusUser status = StatusUser.ACTIVE;
    private Date createdDate;
    private String createdBy;
    private String email;
    private String newPassword;
    private String retypePassword;
    private List<String> listPermission;
}
