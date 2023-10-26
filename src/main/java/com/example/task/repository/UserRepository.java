package com.example.task.repository;

import com.example.task.entity.UserEntity;
import org.apache.el.util.Validation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity>, QuerydslPredicateExecutor<UserEntity> {
    Optional<UserEntity> findByUsernameAndStatus(String username, String status);

    Optional<UserEntity> findByUsername(String username);

    int countByUsernameStartsWith(String username);

    @Query("select username from UserEntity")
    List<String> findAllUsername();

    @Query(value = "select distinct(gp.code) from user u inner join user_group ug inner join group_ gp " +
            " on  u.id = ug.user_id and ug.group_id = gp.id where u.username = ?1"
            , nativeQuery = true)
    List<String> findAllPermissionByUsername(String username);

    @Query(value = "select u from UserEntity u inner join FollowEntity f on f.userId = u.id where f.taskId=?1")
    List<UserEntity> findByFollowTask(Long taskId);
}

