package com.example.task.service.impl;

import com.example.task.dto.GroupDTO;
import com.example.task.dto.UserDTO;
import com.example.task.dto.UserGroupDTO;
import com.example.task.dto.constant.CodePermission;
import com.example.task.dto.constant.LastNameEmail;
import com.example.task.dto.constant.StatusUser;
import com.example.task.entity.GroupEntity;
import com.example.task.entity.UserEntity;
import com.example.task.exception.CustomAppException;
import com.example.task.mail.SendEmailService;
import com.example.task.repository.GroupRepository;
import com.example.task.repository.PermissionRepository;
import com.example.task.repository.UserGroupRepository;
import com.example.task.repository.UserRepository;
import com.example.task.service.IGroupService;
import com.example.task.service.IUserGroupService;
import com.example.task.service.IUserService;
import com.example.task.service.specifications.Filter.Filter;
import com.example.task.service.specifications.Filter.Filter.QueryOperator;
import com.example.task.service.specifications.Filter.FilterBuilder;
import com.example.task.service.specifications.UserSearch;
import com.example.task.transformer.UserTransformer;
import com.example.task.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
import static com.example.task.dto.constant.Roles.admin;
import static com.example.task.dto.constant.StatusUser.INACTIVE;

@Service
public class UserService extends BaseService implements IUserService {
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
    private SendEmailService sendEmailService;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private UserTransformer userTransformer;
    @Autowired
    private UserSearch userSearch;


    @Override
    public List<GrantedAuthority> findByListAuthorities(String username) {
        Optional<UserEntity> userEntity = userRepository.findByUsernameAndStatus(username, String.valueOf(StatusUser.ACTIVE));
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
    public void delete(Long[] ids) {
        for (Long item : ids) {
            userGroupService.deleteByUserId(item);
            UserDTO userDTO = findById(item);
            userDTO.setStatus(INACTIVE);
            update(userDTO);
        }
    }

    @Override
    public List<UserDTO> findAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(item -> userTransformer.toDto(item))
                .getContent();
    }

    @Override
    public Optional<UserDTO> findByUsername(String username) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        if (userEntity.isPresent()) {
            return userEntity.map(item -> modelMapper.map(userEntity, UserDTO.class));
        }
        return Optional.empty();
    }

    @Override
    public int getTotalItem() {
        return (int) userRepository.count();
    }

    @Override
    public UserDTO findById(Long id) {
        return modelMapper.map(userRepository.findById(id), UserDTO.class);
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        userDTO.setUsername(createUsername(userDTO.getFullName()));
        userDTO.setEmail(createEmail(userDTO.getUsername()));


        UserEntity user = modelMapper.map(userDTO, UserEntity.class);

        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userDTO = modelMapper.map(userRepository.save(user), UserDTO.class);

        addGroup(userDTO);

        return userDTO;
    }

    private void addGroup(UserDTO userDTO) {
        UserGroupDTO userGroupDTO = new UserGroupDTO();
        userGroupDTO.setUserId(userDTO.getId());
        userGroupDTO.setGroupId(getGroupIdByRole(String.valueOf(LOGIN)));
        userGroupDTO.setPermissionId(getPermissionIdByRole(String.valueOf(LOGIN)));
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

        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return userTransformer.toDto(userRepository.save(userTransformer.toEntity(userDTO)));
    }

    @Override
    public UserDTO changePassword(UserDTO userDTO) {
        Optional<UserEntity> user = userRepository.findByUsername(userDTO.getUsername());
        if (!user.isPresent()) {
            throw new RuntimeException();
        }
        UserEntity userEntity = user.get();
        if (passwordEncoder.matches(userDTO.getNewPassword(), userEntity.getPassword())) {
            throw new RuntimeException();
        }
        userEntity.setPassword(passwordEncoder.encode(userDTO.getNewPassword()));
        return modelMapper.map(userRepository.save(userEntity), UserDTO.class);
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
    public String getMailDefault() {
        return userRepository.findByUsername(String.valueOf(admin)).get().getEmail();
    }

    private List<UserDTO> findByRole(String role) {
        Optional<GroupEntity> group = groupRepository.findByCode(role);
        List<Long> userGroupEntities = userGroupRepository.findByGroupId(group.get().getId());

        if (userGroupEntities.isEmpty()) {
            return Collections.emptyList();
        }

        return userGroupEntities.stream()
                .map(item -> userRepository.findById(item))
                .map(item -> userTransformer.opToDto(item))
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
    public Page<UserDTO> query(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(item -> userTransformer.toDto(item));
    }

    @Override
    public List<UserDTO> searchUser(String search) {
        Filter filter = new FilterBuilder()
                .buildField(UserEntity.Fields.fullName)
                .buildOperator(QueryOperator.LIKE)
                .buildValue(search)
                .build();
        Specification<UserEntity> specification = userSearch.createSpecification(filter);
        return userRepository.findAll(specification)
                .stream()
                .map(userTransformer::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<UserDTO> querySearch(String search, Pageable pageable) {
        Filter filter = new FilterBuilder()
                .buildField(UserEntity.Fields.fullName)
                .buildOperator(QueryOperator.LIKE)
                .buildValue(search)
                .build();
        Specification<UserEntity> specification = userSearch.createSpecification(filter);
        return userRepository.findAll(specification, pageable)
                .map(userTransformer::toDto);
    }

    @Override
    public void deleteUser(Long[] ids) {
        for (Long item : ids) {
            Optional<UserEntity> user = userRepository.findById(item);
            user.ifPresent(userEntity -> userEntity.setStatus(String.valueOf(INACTIVE)));
        }
    }

    @Override
    public List<String> findAllUsername() {
        return userRepository.findAllUsername();
    }

    @Override
    public List<String> getListPermission(String username) {
        return userRepository.findAllPermissionByUsername(username);
    }
}
