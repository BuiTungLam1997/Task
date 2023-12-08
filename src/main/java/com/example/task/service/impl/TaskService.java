package com.example.task.service.impl;

import com.example.task.dto.EmailDTO;
import com.example.task.dto.TaskDTO;
import com.example.task.entity.UserEntity;
import com.example.task.repository.EmailRepository;
import com.example.task.repository.TaskRepository;
import com.example.task.repository.UserRepository;
import com.example.task.service.ITaskService;
import com.example.task.transformer.EmailTransformer;
import com.example.task.transformer.TaskTransformer;
import com.example.task.utils.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.task.dto.constant.StatusSent.UNSENT;
import static com.example.task.service.impl.EmailService.getMailDefault;

@Service
@AllArgsConstructor
public class TaskService implements ITaskService {


    private TaskRepository taskRepository;

    private TaskTransformer taskTransformer;
    private UserRepository userRepository;
    private EmailRepository emailRepository;
    private EmailTransformer emailTransformer;


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
        return taskTransformer.toDto(taskRepository.save(taskTransformer.toEntity(taskDTO)));
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
                .map(taskTransformer::toDto);
    }

    @Override
    public boolean isValidDate(TaskDTO taskDTO) {
        boolean validDeadlineStart = !taskDTO.getDeadlineStart().isBefore(LocalDate.now());
        boolean validDeadlineEnd = taskDTO.getDeadlineStart().isBefore(taskDTO.getDeadlineEnd());
        return validDeadlineStart && validDeadlineEnd;
    }

    @Override
    public List<TaskDTO> expire() {
        return taskRepository.findLessThanOrEqualDeadlineEnd(LocalDate.now())
                .stream()
                .map(taskTransformer::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> findFollowByUserId() {
        String username = SecurityUtils.getPrincipal().getUsername();
        return taskRepository.findFollowByUserId(userRepository.findByUsername(username)
                        .orElseThrow(NullPointerException::new)
                        .getId())
                .stream()
                .map(taskTransformer::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<TaskDTO> findByPeriod(Long userId, int period) {
        return new PageImpl<>(taskRepository.findByPeriod(userId, period)
                .stream()
                .map(taskTransformer::toDto)
                .collect(Collectors.toList()));
    }

    @Override
    public int totalItemByPeriod(Long userId, int period) {
        return findByPeriod(userId, period).getSize();
    }

    @Override
    public void saveEmail(TaskDTO taskDTO) {
        Optional<UserEntity> user = userRepository.findByUsername(taskDTO.getPerformer());
        String to = user.isPresent() ? user.get().getEmail() : getMailDefault();
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setToEmail(to);
        emailDTO.setTitle(taskDTO.getTitle());
        emailDTO.setContent(taskDTO.getContent());
        emailDTO.setStatusSent(String.valueOf(UNSENT));
        emailRepository.save(emailTransformer.toEntity(emailDTO));
    }


    private String getEmailByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(NullPointerException::new).getEmail();

    }

    @Override
    public void setEmail(TaskDTO taskOld, TaskDTO taskDTO) {
        EmailDTO email = new EmailDTO();
        email.setToEmail(getEmailByUsername(taskDTO.getCreatedBy()));
        saveEmail(taskOld, taskDTO, email);

        List<UserEntity> users = userRepository.findByFollowTask(taskDTO.getId());
        for (UserEntity user : users) {
            email.setToEmail(user.getEmail());
            saveEmail(taskOld, taskDTO, email);
        }

        if (taskDTO.getPerformer().equals(taskOld.getPerformer())) {
            email.setToEmail(getEmailByUsername(taskOld.getPerformer()));
            saveEmail(taskOld, taskDTO, email);
        } else {
            email.setToEmail(getEmailByUsername(taskOld.getPerformer()));
            saveEmail(taskOld, taskDTO, email);

            email.setToEmail(getEmailByUsername(taskDTO.getPerformer()));
            saveEmail(taskOld, taskDTO, email);
        }
    }

    @Override
    public List<TaskDTO> findAll() {
        return taskRepository.findAll().stream()
                .map(item -> taskTransformer.toDto(item))
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> findAll(Pageable pageable) {
        return taskRepository.findAll().stream()
                .map(taskTransformer::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<TaskDTO> findById(Long id) {
        return taskRepository.findById(id).map(taskTransformer::toDto);
    }

    @Override
    public Page<TaskDTO> query(Pageable pageable) {
        return taskRepository.findAll(pageable)
                .map(item -> taskTransformer.toDto(item));
    }

    private void saveEmail(TaskDTO taskOld, TaskDTO taskDTO, EmailDTO email) {
        String title = "Thông báo thay đổi task có tiêu đề " + taskDTO.getTitle().toUpperCase() +
                " thành " +
                taskDTO.getTitle().toUpperCase();
        email.setTitle(title);

        String content = "Thay đổi task có nội dung từ " + taskOld.getContent().toUpperCase() +
                " thành " +
                taskDTO.getContent().toUpperCase() +
                " trang thái từ " +
                taskOld.getStatus() +
                " thành " +
                taskDTO.getStatus() +
                ". Người thực hiện " + taskDTO.getPerformer().toUpperCase();
        email.setContent(content);
        email.setStatusSent(String.valueOf(UNSENT));
        emailRepository.save(emailTransformer.toEntity(email));
    }

}
