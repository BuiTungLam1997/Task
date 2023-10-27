package com.example.task.dto;

import lombok.Data;

@Data
public class FollowDTO extends BaseDTO<FollowDTO> {
    private Long userId;
    private Long taskId;
    private Long[] taskIds;
}
