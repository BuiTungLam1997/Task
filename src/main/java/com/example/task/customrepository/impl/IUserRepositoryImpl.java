package com.example.task.customrepository.impl;

import com.example.task.entity.*;
import com.example.task.customrepository.IUserRepository;
import com.example.task.repository.specifications.UserSearch;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class IUserRepositoryImpl implements IUserRepository {
    private final QUserEntity qUserEntity = QUserEntity.userEntity;
    private final QUserGroupEntity qUserGroupEntity = QUserGroupEntity.userGroupEntity;
    private final QGroupEntity qGroupEntity = QGroupEntity.groupEntity;

    private EntityManager em;

    @Override
    public List<String> findAllUsername() {
        JPAQuery<UserEntity> query = new JPAQuery<>(em);
        return query.select(qUserEntity.username).from(qUserEntity).fetch();
    }

    @Override
    public List<GroupEntity> findAllPermissionByUsername(String username) {
        JPAQuery<GroupEntity> query = new JPAQuery<>(em);
        return query.select(qGroupEntity)
                .distinct()
                .from(qGroupEntity)
                .innerJoin(qUserGroupEntity)
                .on(qGroupEntity.id.eq(qUserGroupEntity.groupId))
                .innerJoin(qUserEntity)
                .on(qUserEntity.id.eq(qUserGroupEntity.userId))
                .where(qUserEntity.username.eq(username))
                .fetch();
    }
}
