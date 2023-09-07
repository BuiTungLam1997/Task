package com.example.task.service.impl;

import com.example.task.dto.GroupDTO;
import com.example.task.dto.UserDTO;
import com.example.task.dto.UserGroupDTO;
import com.example.task.dto.constant.CodePermission;
import com.example.task.dto.constant.StatusUser;
import com.example.task.entity.GroupEntity;
import com.example.task.entity.UserEntity;
import com.example.task.exception.CustomAppException;
import com.example.task.mail.emailService;
import com.example.task.repository.GroupRepository;
import com.example.task.repository.PermissionRepository;
import com.example.task.repository.UserGroupRepository;
import com.example.task.repository.UserRepository;
import com.example.task.service.IGroupService;
import com.example.task.service.IUserGroupService;
import com.example.task.service.IUserService;
import com.example.task.utils.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.task.dto.constant.Roles.LOGIN;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserGroupRepository userGroupRepository;
    @Autowired
    private IGroupService groupService;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IUserGroupService userGroupService;
    @Autowired
    private emailService emailService;
    @Autowired
    private PermissionRepository permissionRepository;
    ModelMapper mapper = new ModelMapper();


    @Override
    public List<GrantedAuthority> findByListAuthorities(String username) {
        Optional<UserEntity> userEntity = userRepository.findByUsernameAndStatus(username, StatusUser.ACTIVE);
        if (userEntity.isPresent()) {
            List<GrantedAuthority> authorities = new ArrayList<>();
            try {
                List<GroupDTO> groupDTOS = groupService.findByUserId(userEntity.get().getId());
                for (GroupDTO item : groupDTOS) {
                    authorities.add(new SimpleGrantedAuthority(item.getCode()));
                }
                return authorities;
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        return Collections.emptyList();
    }

    @Override
    public List<UserDTO> findAll(Pageable pageable) {
        List<UserEntity> userEntities = userRepository.findAll(pageable).getContent();

        return userEntities.stream()
                .map(item -> mapper.map(item, UserDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDTO> findByUsername(String username) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        if (userEntity.isPresent()) {
            return userEntity.map(item -> mapper.map(userEntity, UserDTO.class));
        }
        return Optional.empty();
    }

    @Override
    public int getTotalItem() {
        return (int) userRepository.count();
    }

    @Override
    public UserDTO findById(Long id) {
        return mapper.map(userRepository.findById(id), UserDTO.class);
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        userDTO.setUsername(createUsername(userDTO.getFullName()));
        userDTO.setEmail(createEmail(userDTO.getUsername()));


        UserEntity user = mapper.map(userDTO, UserEntity.class);

        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userDTO = mapper.map(userRepository.save(user), UserDTO.class);

        addGroup(userDTO);

        return userDTO;
    }

    private void addGroup(UserDTO userDTO) {
        UserGroupDTO userGroupDTO = new UserGroupDTO();
        userGroupDTO.setUserId(userDTO.getId());
        userGroupDTO.setGroupId(getGroupIdByRole(LOGIN));
        userGroupDTO.setPermissionId(getPermissionIdByRole(LOGIN));
        userGroupService.save(userGroupDTO);
    }

    private Long getGroupIdByRole(String role) {
        return groupRepository.findByCode(role).get().getId();
    }

    private Long getPermissionIdByRole(String role) {
        return permissionRepository.findByCode(role).get().getId();
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        UserEntity user = mapper.map(userDTO, UserEntity.class);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return mapper.map(userRepository.save(user), UserDTO.class);
    }

    @Override
    public UserDTO changePassword(UserDTO userDTO) {
        Optional<UserEntity> user = userRepository.findByUsername(userDTO.getUsername());
        user.get().setPassword(passwordEncoder.encode(userDTO.getNewPassword()));
        UserEntity userEntity = mapper.map(user, UserEntity.class);
        return mapper.map(userRepository.save(userEntity), UserDTO.class);
    }

    @Override
    public boolean checkPassword(String oldPassword, String newPassword) {
        return oldPassword.equals(newPassword);
    }

    @Override
    public Boolean isExist(String username) {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        return user.isPresent();
    }

    @Override
    public List<UserDTO> findByAdminUser() {
        String role = CodePermission.ADMIN_USER;
        return findByRole(role);
    }

    @Override
    public List<UserDTO> findByAdminTask() {
        String role = CodePermission.ADMIN_TASK;
        return findByRole(role);
    }

    @Override
    public void sendMail(String to, String title, String content) {
        emailService.sendMail(to, title, content);
    }

    private List<UserDTO> findByRole(String role) {
        Optional<GroupEntity> group = groupRepository.findByCode(role);
        List<Long> userGroupEntities = userGroupRepository.findByGroupId(group.get().getId());

        if (userGroupEntities.isEmpty()) {
            return Collections.emptyList();
        }

        return userGroupEntities.stream()
                .map(item -> userRepository.findById(item))
                .map(item -> mapper.map(item, UserDTO.class))
                .collect(Collectors.toList());
    }

    private String createUsername(String fullName) {
        StringBuilder username = new StringBuilder();
        String firstUser = "";
        String lastUser = "";
        String[] split = fullName.split("\\s");

        lastUser = StringUtils.removeAccent(split[0]);
        firstUser = StringUtils.removeAccent(split[split.length - 1]);
        username.append(firstUser).append(".").append(lastUser);

        int lastNumberUser = userRepository.lastNumberUser(username.toString().toLowerCase());
        if (lastNumberUser != 0) {
            username.append(lastNumberUser);
        }
        return username.toString().toLowerCase();
    }

    private String createEmail(String username) {
        String lastEmail = "@gmail.com";
        return username + lastEmail;
    }

    public UserDTO getUser(String username) {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (!user.isPresent()) {
            throw new CustomAppException(404, "User not found");
        }
        return mapper.map(user, UserDTO.class);
    }
}
