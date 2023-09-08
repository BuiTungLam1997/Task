package com.example.task.service;

import com.example.task.dto.UserDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<GrantedAuthority> findByListAuthorities(String username);
    List<UserDTO> findAll(Pageable pageable);
    Optional<UserDTO> findByUsername(String username);
    int getTotalItem();
    UserDTO findById(Long id);
    UserDTO save(UserDTO userDTO);
    UserDTO update(UserDTO userDTO);
    UserDTO changePassword(UserDTO userDTO);
    boolean checkPassword(String oldPassword,String newPassword);
    Boolean isExist(String username);

    List<UserDTO> findByAdminUser();
    List<UserDTO> findByAdminTask();
    void sendMail(String to ,String title,String content);
    String getMailDefault();
}
