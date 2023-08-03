package com.example.task.repository;

import com.example.task.entity.EmailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmailRepository extends JpaRepository<EmailEntity, Long> {
    @Query(value = "select * from email where status_sent = ?1 order by created_date asc limit ?2"
            ,nativeQuery = true)
    List<EmailEntity> findByStatusSent(String statusSent,int limit);

}
