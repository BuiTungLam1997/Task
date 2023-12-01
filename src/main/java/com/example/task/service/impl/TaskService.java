package com.example.task.service.impl;

import com.example.task.dto.EmailDTO;
import com.example.task.dto.TaskDTO;
import com.example.task.dto.UserDTO;
import com.example.task.entity.QTaskEntity;
import com.example.task.entity.TaskEntity;
import com.example.task.repository.TaskRepository;
import com.example.task.repository.specifications.TaskSearch;
import com.example.task.service.IEmailService;
import com.example.task.service.ITaskService;
import com.example.task.service.IUserService;
import com.example.task.transformer.CommonTransformer;
import com.example.task.transformer.TaskTransformer;
import com.example.task.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.task.dto.constant.StatusSent.UNSENT;

@Service
public class TaskService extends BaseService<TaskDTO, TaskEntity, TaskRepository> implements ITaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskTransformer taskTransformer;
    @Autowired
    private TaskSearch taskSearch;
    @Autowired
    private IUserService userService;
    @Autowired
    private IEmailService emailService;
    private final QTaskEntity QTask = QTaskEntity.taskEntity;

    public TaskService(TaskRepository repo, CommonTransformer<TaskDTO, TaskEntity> transformer, EntityManager em) {
        super(repo, transformer, em);
    }

    @Override
    public Page<TaskDTO> findAllByUsername(Pageable pageable, String username) {
        return taskRepository.findAllByPerformer(pageable, username).map(taskTransformer::toDto);
    }

    @Override
    public Integer getTotalItemByUsername(String username) {
        return taskRepository.countByPerformer(username);
    }


    @Override
    public TaskDTO save(TaskDTO taskDTO) {
        return taskTransformer.toDto(taskRepository.save(transformer.toEntity(taskDTO)));
    }

    @Override
    public TaskDTO update(TaskDTO taskDTO) {
        return taskTransformer.toDto(taskRepository.save(taskTransformer.toEntity(taskDTO)));
    }

    @Override
    public void delete(Long[] ids) {
        for (Long item : ids) {
            taskRepository.deleteById(item);
        }
    }

    @Override
    public Page<TaskDTO> searchTask(Pageable pageable, String search) {
        return taskRepository.searchTask(pageable, search)
                .map(transformer::toDto);
    }

    @Override
    public boolean isValidDate(TaskDTO taskDTO) {
        int day = taskDTO.getDeadlineStart().compareTo(LocalDate.now());
        int day1 = taskDTO.getDeadlineEnd().compareTo(taskDTO.getDeadlineStart());
        return day >= 0 && day1 > 0;
    }

    @Override
    public List<TaskDTO> expire() {
        List<TaskEntity> taskEntity = taskRepository.findLessThanOrEqualDeadlineEnd(LocalDate.now());
        return taskEntity.stream()
                .map(taskTransformer::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> findFollowByUserId() {
        String username = SecurityUtils.getPrincipal().getUsername();
        UserDTO user = userService.findByUsername(username).orElseThrow(NullPointerException::new);
        return taskRepository.findFollowByUserId(user.getId())
                .stream()
                .map(transformer::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> findByPeriod(Long userId, int period) {
        return taskRepository.findByPeriod(userId, period).stream().map(transformer::toDto).collect(Collectors.toList());
    }

    @Override
    public int totalItemByPeriod(Long userId, int period) {
        return findByPeriod(userId, period).size();
    }

    @Override
    public void saveEmail(TaskDTO taskDTO) {
        Optional<UserDTO> userDTO = userService.findByUsername(taskDTO.getPerformer());
        String to = userDTO.isPresent() ? userDTO.get().getEmail() : userService.getMailDefault();
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setToEmail(to);
        emailDTO.setTitle(taskDTO.getTitle());
        emailDTO.setContent(taskDTO.getContent());
        emailDTO.setStatusSent(String.valueOf(UNSENT));
        emailService.save(emailDTO);
    }
}
