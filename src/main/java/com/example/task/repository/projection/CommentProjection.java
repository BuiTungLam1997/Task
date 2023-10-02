package com.example.task.repository.projection;

import com.example.task.entity.CommentEntity;

public class CommentProjection {

   public interface CommentWithFullName {
       Long getId();
       Long getTaskId();
       Long getUserId();
       String getFullName();
       String getContent();
   }

   public static CommentEntity toEntity(CommentWithFullName projection) {
        return CommentEntity.builder()
                .fullName(projection.getFullName())
                .content(projection.getContent())
                .userId(projection.getUserId())
                .taskId(projection.getTaskId())
                .id(projection.getId())
                .build();
   }
}
