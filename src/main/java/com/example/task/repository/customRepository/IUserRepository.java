package com.example.task.repository.customRepository;

import com.example.task.dto.GroupDTO;
import com.example.task.dto.PermissionDTO;
import com.example.task.dto.UserDTO;
import com.example.task.entity.GroupEntity;
import com.example.task.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IUserRepository {
    Optional<UserEntity> findByUsername(String username);
    List<String> findAllUsername();
    List<GroupEntity> findAllPermissionByUsername(String username);
}
