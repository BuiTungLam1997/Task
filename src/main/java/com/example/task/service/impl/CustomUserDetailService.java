package com.example.task.service.impl;

import com.example.task.dto.MyUser;
import com.example.task.dto.constant.StatusUser;
import com.example.task.entity.UserEntity;
import com.example.task.repository.UserRepository;
import com.example.task.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Optional<UserEntity> userEntity = userRepository.findByUsernameAndStatus(username, String.valueOf(StatusUser.ACTIVE));
            if (userEntity.isPresent()) {
                List<GrantedAuthority> authorities = userService.findByListAuthorities(username);
                UserEntity user = userEntity.get();
                MyUser userDetails = new MyUser(user.getUsername(), user.getPassword(), authorities);
                userDetails.setFullName(userEntity.get().getFullName());
                return userDetails;
            } else {
                new UsernameNotFoundException("Login fail!");
                return null;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
