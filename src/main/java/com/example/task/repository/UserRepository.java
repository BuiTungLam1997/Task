package com.example.task.repository;

import com.example.task.dto.UserDTO;
import com.example.task.entity.UserEntity;
import com.example.task.repository.customRepository.IUserRepository;
import com.example.task.repository.specifications.UserSearch;
import com.example.task.service.builderpattern.Filter.Filter;
import com.example.task.service.builderpattern.Filter.FilterBuilder;
import org.apache.el.util.Validation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity>, QuerydslPredicateExecutor<UserEntity>, IUserRepository {
    Optional<UserEntity> findByUsernameAndStatus(String username, String status);

    Optional<UserEntity> findByUsername(String username);

    int countByUsernameStartsWith(String username);

    @Query("select username from UserEntity")
    List<String> findAllUsername();


    @Query(value = "select u from UserEntity u inner join FollowEntity f on f.userId = u.id where f.taskId=?1")
    List<UserEntity> findByFollowTask(Long taskId);

    default Page<UserEntity> querySearch(String search, Pageable pageable) {
        UserSearch userSearch = new UserSearch();
        Filter filter = new FilterBuilder()
                .buildField(UserEntity.Fields.fullName)
                .buildOperator(Filter.QueryOperator.LIKE)
                .buildValue(search)
                .build();
        Specification<UserEntity> specification = userSearch.createSpecification(filter);
        return findAll(specification, pageable);
    }
}

