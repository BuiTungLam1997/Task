package com.example.task.service.impl;

import com.example.task.constant.SystemConstant;
import com.example.task.dto.GroupDTO;
import com.example.task.dto.MyUser;
import com.example.task.entity.UserEntity;
import com.example.task.repository.GroupRepository;
import com.example.task.repository.UserGroupRepository;
import com.example.task.repository.UserRepository;
import com.example.task.service.IGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserGroupRepository userGroupRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private IGroupService groupService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsernameAndStatus(username, SystemConstant.ACTIVE_STATUS);

        if (userEntity == null) {
            throw new UsernameNotFoundException("User not found");
        }
        List<GrantedAuthority> authorities = new ArrayList<>();//list group
        List<GroupDTO> listGroup = groupService.findByUserId(userEntity.getId());
        for (GroupDTO group : listGroup) {
            authorities.add(new SimpleGrantedAuthority(group.getName()));
        }
        MyUser myUser = new MyUser(userEntity.getUsername(), userEntity.getPassword(), true,
                true, true, true, authorities);
        myUser.setFullName(userEntity.getFullName());
        return myUser;
    }

}
