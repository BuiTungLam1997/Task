package com.example.task.customrepository;

import com.example.task.entity.GroupEntity;
import com.example.task.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface IUserRepository {
    List<String> findAllUsername();
    List<GroupEntity> findAllPermissionByUsername(String username);
}
