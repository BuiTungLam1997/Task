package com.example.task.service;

import com.example.task.dto.GroupDTO;
import com.example.task.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<GrantedAuthority> findByListAuthorities(String username);

    void delete(Long[] ids);

    Optional<UserDTO> findByUsername(String username);

    UserDTO save(UserDTO userDTO);

    UserDTO update(UserDTO userDTO);

    UserDTO changePassword(UserDTO userDTO);

    boolean checkPassword(String oldPassword, String newPassword);

    Boolean isExist(String username);

    List<UserDTO> findByAdminUser();

    List<UserDTO> findByAdminTask();

    void sendMail(String to, String title, String content);

    Page<UserDTO> querySearch(String search, Pageable pageable);

    void deleteUser(Long[] ids);

    List<String> findAllUsername();

    List<GroupDTO> getListPermission(String username);

    List<UserDTO> findByFollow(Long taskId);

    List<UserDTO> findAll();

    Optional<UserDTO> findById(Long id);

    Page<UserDTO> query(Pageable pageable);
}
