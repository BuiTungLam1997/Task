package com.example.task.dto;

import lombok.Data;

@Data

public class UserGroupDTO extends BaseDTO<UserGroupDTO> {

    private Long userId;
    private Long groupId;

    private Long[] userIds;
}
