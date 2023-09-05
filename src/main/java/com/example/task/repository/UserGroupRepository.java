package com.example.task.repository;

import com.example.task.entity.UserGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserGroupRepository extends JpaRepository<UserGroupEntity,Long> {

    @Query(value = "SELECT DISTINCT(GROUP_ID) FROM USERGROUP u WHERE u.USER_ID = ?1",
            nativeQuery = true)
    List<Long> findByUserId(Long userId);

    @Query(value = "SELECT DISTINCT(USER_ID) FROM USERGROUP u WHERE u.GROUP_ID = ?1",
            nativeQuery = true)
    List<Long> findByGroupId(Long groupId);
}
