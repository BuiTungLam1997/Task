package com.example.task.service.impl;

import com.example.task.dto.GroupDTO;
import com.example.task.dto.UserDTO;
import com.example.task.dto.UserGroupDTO;
import com.example.task.dto.constant.CodePermission;
import com.example.task.dto.constant.LastNameEmail;
import com.example.task.dto.constant.StatusUser;
import com.example.task.entity.BaseEntity;
import com.example.task.entity.GroupEntity;
import com.example.task.entity.UserEntity;
import com.example.task.exception.NullPointException;
import com.example.task.mail.SendEmailService;
import com.example.task.repository.GroupRepository;
import com.example.task.repository.UserGroupRepository;
import com.example.task.repository.UserRepository;
import com.example.task.service.IUserService;
import com.example.task.transformer.GroupTransformer;
import com.example.task.transformer.UserGroupTransformer;
import com.example.task.transformer.UserTransformer;
import com.example.task.utils.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.task.dto.constant.Roles.LOGIN;
import static com.example.task.dto.constant.StatusUser.INACTIVE;

@Service
@AllArgsConstructor
public class UserService implements IUserService {

    private UserRepository userRepository;

    private GroupRepository groupRepository;

    private PasswordEncoder passwordEncoder;

    private UserGroupRepository userGroupRepository;

    private SendEmailService sendEmailService;

    private UserTransformer userTransformer;

    private GroupTransformer groupTransformer;

    private UserGroupTransformer userGroupTransformer;


    @Override
    public List<GrantedAuthority> findByListAuthorities(String username) {
        Optional<UserEntity> userEntity = userRepository.findByUsernameAndStatus(username, String.valueOf(StatusUser.ACTIVE));
        if (userEntity.isPresent()) {
            List<GrantedAuthority> authorities = new ArrayList<>();
            try {
                List<Long> groupIds = userGroupRepository.findByUserId(userEntity.get().getId());
                for (Long item : groupIds) {
                    GroupDTO groupDTO = groupRepository.findById(item).map(groupTransformer::toDto).orElseThrow(NullPointerException::new);
                    authorities.add(new SimpleGrantedAuthority(groupDTO.getCode()));
                }
                return authorities;
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        return Collections.emptyList();
    }

    @Override
    @Transactional
    public void delete(Long[] ids) {
        userGroupRepository.deleteAllById(Arrays.asList(ids));
        for (Long item : ids) {
            Optional<UserEntity> user = userRepository.findById(item);
            user.ifPresent(userEntity -> userEntity.setStatus(String.valueOf(INACTIVE)));
            user.map(userEntity -> userRepository.save(userEntity));
        }
    }

    @Override
    public Optional<UserDTO> findByUsername(String username) {
        return Optional.ofNullable(userTransformer.opToDto(userRepository.findByUsername(username)));
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        userDTO.setUsername(createUsername(userDTO.getFullName()));
        userDTO.setEmail(createEmail(userDTO.getUsername()));

        UserEntity user = userTransformer.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userDTO = userTransformer.toDto(user);
        addGroup(userDTO);

        return userDTO;
    }

    private void addGroup(UserDTO userDTO) {
        UserGroupDTO userGroupDTO = new UserGroupDTO();
        userGroupDTO.setUserId(userDTO.getId());
        userGroupDTO.setGroupId(getGroupIdByRole(String.valueOf(LOGIN)));
        userGroupRepository.save(userGroupTransformer.toEntity(userGroupDTO));
    }

    private Long getGroupIdByRole(String role) {
        return groupRepository.findByCode(role).map(BaseEntity::getId).orElseThrow(NullPointerException::new);
    }

    @Override
    public UserDTO update(UserDTO userDTO) {

        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return userTransformer.toDto(userRepository.save(userTransformer.toEntity(userDTO)));
    }

    @Override
    public UserDTO changePassword(UserDTO userDTO) {
        Optional<UserEntity> user = userRepository.findByUsername(userDTO.getUsername());
        if (!user.isPresent()) {
            throw new RuntimeException("Không tồn tại user");
        }
        UserEntity userEntity = user.get();
        if (passwordEncoder.matches(userDTO.getNewPassword(), userEntity.getPassword())) {
            throw new RuntimeException("Password sau khi thay đổi giống password cũ!");
        }
        userEntity.setPassword(passwordEncoder.encode(userDTO.getNewPassword()));
        return userTransformer.toDto(userRepository.save(userEntity));
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
        String role = String.valueOf(CodePermission.ADMIN_USER);
        return findByRole(role);
    }

    @Override
    public List<UserDTO> findByAdminTask() {
        String role = String.valueOf(CodePermission.ADMIN_TASK);
        return findByRole(role);
    }

    @Override
    public void sendMail(String to, String title, String content) {
        sendEmailService.sendMail(to, title, content);
    }

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(item -> userTransformer.toDto(item))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDTO> findById(Long id) {
        return userRepository.findById(id).map(userTransformer::toDto);
    }

    @Override
    public Page<UserDTO> query(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userTransformer::toDto);
    }

    private List<UserDTO> findByRole(String role) {
        Optional<GroupEntity> group = groupRepository.findByCode(role);
        List<Long> userGroupEntities = userGroupRepository.findByGroupId(group.map(BaseEntity::getId).orElseThrow(NullPointException::new));
        return userGroupEntities.stream()
                .map(item -> userRepository.findById(item))
                .map(item -> userTransformer.opToDto(item))
                .collect(Collectors.toList());
    }

    private String createUsername(String fullName) {
        StringBuilder username = new StringBuilder();
        String firstUser;
        String lastUser;
        String[] split = fullName.split("\\s");

        lastUser = StringUtils.removeAccent(split[0]);
        firstUser = StringUtils.removeAccent(split[split.length - 1]);
        username.append(firstUser).append(".").append(lastUser);

        int lastNumberUser = userRepository.countByUsernameStartsWith(username.toString().toLowerCase());
        if (lastNumberUser != 0) {
            username.append(lastNumberUser);
        }
        return username.toString().toLowerCase();
    }

    private String createEmail(String username) {
        String lastEmail = LastNameEmail.EMAIL;
        return username + lastEmail;
    }

    @Override
    public Page<UserDTO> querySearch(String search, Pageable pageable) {
        return userRepository.querySearch(search, pageable)
                .map(userTransformer::toDto);
    }

    @Override
    @Transactional
    public void deleteUser(Long[] ids) {
        for (Long item : ids) {
            Optional<UserEntity> user = userRepository.findById(item);
            user.ifPresent(userEntity -> userEntity.setStatus(String.valueOf(INACTIVE)));
            user.map(userEntity -> userRepository.save(userEntity));
        }
    }

    @Override
    public List<String> findAllUsername() {
        return userRepository.findAllUsername();
    }

    @Override
    public List<GroupDTO> getListPermission(String username) {
        return userRepository.findAllPermissionByUsername(username)
                .stream()
                .map(groupTransformer::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> findByFollow(Long taskId) {
        return userRepository.findByFollowTask(taskId)
                .stream()
                .map(userTransformer::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserEntity> findByUsernameAndStatus(String username, String status) {
        return userRepository.findByUsernameAndStatus(username, status);
    }
}
