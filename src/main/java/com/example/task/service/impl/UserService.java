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
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

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
    ModelMapper mapper = new ModelMapper();


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
        return null;
    }

    @Override
    public List<UserDTO> findAll(Pageable pageable) {
        List<UserEntity> userEntities = userRepository.findAll(pageable).getContent();
        List<UserDTO> userDTOS = new ArrayList<>();
        for (UserEntity item : userEntities) {
            userDTOS.add(mapper.map(item, UserDTO.class));
        }
        return userDTOS;
    }

    @Override
    public Optional<UserDTO> findByUsername(String username) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        if (userEntity.isPresent()) {
            return Optional.ofNullable(mapper.map(userEntity, UserDTO.class));
        }
        return null;
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

        String password = String.valueOf(randomPassword());

        UserEntity user = mapper.map(userDTO, UserEntity.class);

        user.setPassword(passwordEncoder.encode(password));
        userDTO = mapper.map(userRepository.save(user), UserDTO.class);


        addGroup(userDTO);

        userDTO.setPassword(password);
        return userDTO;
    }

    private void addGroup(UserDTO userDTO) {
        UserGroupDTO userGroupDTO = new UserGroupDTO();
        userGroupDTO.setUserId(userDTO.getId());
        userGroupDTO.setGroupId(1L);
        userGroupDTO.setPermissionId(1L);
        userGroupService.save(userGroupDTO);
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        UserEntity user = mapper.map(userDTO, UserEntity.class);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return mapper.map(userRepository.save(user), UserDTO.class);
    }

    @Override
    public Boolean isExist(String username) {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        return user.isPresent();
    }

    @Override
    public List<UserDTO> findByAdminUser() {
        String role = String.valueOf(CodePermission.ADMIN_USER);
        List<UserDTO> user = new ArrayList<>();
        user = findByRole(role, user);
        return user;
    }

    @Override
    public List<UserDTO> findByAdminTask() {
        List<UserDTO> user = new ArrayList<>();
        String role = String.valueOf(CodePermission.ADMIN_TASK);
        user = findByRole(role, user);
        return user;
    }

    @Override
    public void sendMail(String to, String title, String content) {
        emailService.sendMail(to, title, content);
    }

    private List<UserDTO> findByRole(String role, List<UserDTO> user) {

        Optional<GroupEntity> group;
        Optional<UserEntity> userEntity;
        group = groupRepository.findByCode(role);

        List<Long> userGroupEntities = userGroupRepository.findByGroupId(group.get().getId());
        if (userGroupEntities.isEmpty()) {
            return null;
        }
        for (Long item : userGroupEntities) {
            userEntity = userRepository.findById(item);
            user.add(mapper.map(userEntity, UserDTO.class));
        }
        return user;
    }


    private boolean isUnique(String username) {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return true;
        }
        return false;
    }

    private String createUsername(String fullName) {
        StringBuilder username = new StringBuilder();
        String firstUser = "";
        String lastUser = "";
        String[] split = fullName.split("\\s");
        lastUser = StringUtils.removeAccent(split[0]);
        firstUser = StringUtils.removeAccent(split[split.length - 1]);
        username.append(firstUser).append(".").append(lastUser);
        String userCheck = username.toString();
        int i = 1;
        boolean isEmpty = true;
        while (isUnique(userCheck.toString().toLowerCase())) {
            isEmpty = false;
            userCheck = username.toString();
            String lastNumber = String.valueOf(i);
            userCheck += lastNumber;
            i++;
        }
        if (isEmpty) {
            return username.toString().toLowerCase();
        }
        return username.append(i - 1).toString().toLowerCase();
    }

    private int randomPassword() {
        return ThreadLocalRandom.current().nextInt(111111, 999999);
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
