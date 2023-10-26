package com.example.task.service.impl;

import com.example.task.dto.TaskDTO;
import com.example.task.dto.UserDTO;
import com.example.task.entity.QTaskEntity;
import com.example.task.entity.TaskEntity;
import com.example.task.repository.TaskRepository;
import com.example.task.repository.specifications.TaskSearch;
import com.example.task.service.ITaskService;
import com.example.task.service.IUserService;
import com.example.task.service.builderpattern.Filter.Filter;
import com.example.task.service.builderpattern.Filter.FilterBuilder;
import com.example.task.transformer.CommonTransformer;
import com.example.task.transformer.TaskTransformer;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.task.dto.constant.StatusTask.DONE;
import static com.example.task.service.builderpattern.Filter.Filter.QueryOperator.LIKE;

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
    private QTaskEntity QTask = QTaskEntity.taskEntity;

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
    public Page<TaskDTO> queryExample(Pageable pageable, String search) {
        return taskRepository.findAll(buildFilter(search), pageable)
                .map(taskTransformer::toDto);
    }

    private Specification<TaskEntity> buildFilter(String search) {
        Filter filterTitle = new FilterBuilder()
                .buildField(TaskEntity.Fields.title)
                .buildOperator(LIKE)
                .buildValue(search)
                .build();
        Filter filterPerformer = new FilterBuilder()
                .buildField(TaskEntity.Fields.performer)
                .buildOperator(LIKE)
                .buildValue(search)
                .build();
        Filter filterStatus = new FilterBuilder()
                .buildField(TaskEntity.Fields.status)
                .buildOperator(LIKE)
                .buildValue(search)
                .build();
        return taskSearch.createSpecification(filterTitle)
                .or(taskSearch.createSpecification(filterPerformer))
                .or(taskSearch.createSpecification(filterStatus));
    }

    @Override
    public List<TaskDTO> searchTask(Pageable pageable, String search) {
        return taskRepository.findAll(buildFilter(search), pageable)
                .stream()
                .map(item -> taskTransformer.toDto(item))
                .collect(Collectors.toList());
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
    public List<TaskDTO> findFollowByUserId(Long userId) {
        return taskRepository.findFollowByUserId(userId)
                .stream()
                .map(transformer::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> findByPeriod(Long userId, int period) {
        UserDTO user = userService.findById(userId).orElseThrow(NullPointerException::new);
        JPAQuery<TaskDTO> query = new JPAQuery<>(em);
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(QTask.deadlineEnd.before(LocalDate.now()).and(QTask.deadlineEnd.after(LocalDate.now()
                .minusMonths(period))).and(QTask.performer.eq(user.getUsername())).and(QTask.status.eq(String.valueOf(DONE))));
        return query.select(QTask).from(QTask).where(builder).fetch().stream().map(taskTransformer::toDto).collect(Collectors.toList());
    }

    @Override
    public int totalItemByPeriod(Long userId, int period) {
        return findByPeriod(userId, period).size();
    }
}
