package com.example.task.repository.Impl;

import com.example.task.dto.GroupDTO;
import com.example.task.dto.PermissionDTO;
import com.example.task.entity.*;
import com.example.task.repository.customRepository.IUserRepository;
import com.example.task.repository.specifications.UserSearch;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserRepositoryImpl implements IUserRepository {
    private final QUserEntity qUserEntity = QUserEntity.userEntity;
    private final QUserGroupEntity qUserGroupEntity = QUserGroupEntity.userGroupEntity;
    private final QGroupEntity qGroupEntity = QGroupEntity.groupEntity;

    private EntityManager em;
    private UserSearch userSearch;

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        JPAQuery<UserEntity> query = new JPAQuery<>(em);
        UserEntity user = query.select(qUserEntity).from(qUserEntity).where(qUserEntity.username.eq(username)).fetchFirst();
        if (user == null) return Optional.empty();
        return Optional.of(user);
    }

    @Override
    public List<String> findAllUsername() {
        JPAQuery<UserEntity> query = new JPAQuery<>(em);
        return query.select(qUserEntity.username).from(qUserEntity).fetch();
    }

    @Override
    public List<GroupEntity> findAllPermissionByUsername(String username) {
        JPAQuery<GroupEntity> query = new JPAQuery<>(em);
        List<GroupEntity> list = query.select(qGroupEntity)
                .distinct()
                .from(qGroupEntity)
                .innerJoin(qUserGroupEntity)
                .on(qGroupEntity.id.eq(qUserGroupEntity.groupId))
                .innerJoin(qUserEntity)
                .on(qUserEntity.id.eq(qUserGroupEntity.userId))
                .where(qUserEntity.username.eq(username))
                .fetch();
        return list;
    }
}
