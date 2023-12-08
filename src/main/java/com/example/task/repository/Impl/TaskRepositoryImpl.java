package com.example.task.repository.Impl;

import com.example.task.dto.TaskDTO;
import com.example.task.dto.UserDTO;
import com.example.task.entity.QTaskEntity;
import com.example.task.entity.TaskEntity;
import com.example.task.entity.UserEntity;
import com.example.task.repository.UserRepository;
import com.example.task.repository.customRepository.ITaskRepository;
import com.example.task.repository.specifications.TaskSearch;
import com.example.task.service.IUserService;
import com.example.task.service.builderpattern.Filter.Filter;
import com.example.task.service.builderpattern.Filter.FilterBuilder;
import com.example.task.transformer.TaskTransformer;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static com.example.task.dto.constant.StatusTask.DONE;
import static com.example.task.service.builderpattern.Filter.Filter.QueryOperator.LIKE;

@Repository
@AllArgsConstructor
public class TaskRepositoryImpl implements ITaskRepository {
    EntityManager em;
    final QTaskEntity qTask = QTaskEntity.taskEntity;
    UserRepository userRepository;
    TaskTransformer taskTransformer;
    TaskSearch taskSearch;


    @Override
    public List<TaskEntity> findByPeriod(Long userId, int period) {
        UserEntity user = userRepository.findById(userId).orElseThrow(NullPointerException::new);
        JPAQuery<TaskDTO> query = new JPAQuery<>(em);
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qTask.deadlineEnd.before(LocalDate.now()).and(qTask.deadlineEnd.after(LocalDate.now()
                .minusMonths(period))).and(qTask.performer.eq(user.getUsername())).and(qTask.status.eq(String.valueOf(DONE))));
        return query.select(qTask).from(qTask).where(builder).fetch();
    }
}
