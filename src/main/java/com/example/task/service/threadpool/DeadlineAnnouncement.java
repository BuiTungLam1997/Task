package com.example.task.service.threadpool;

import com.example.task.dto.EmailDTO;
import com.example.task.dto.TaskDTO;
import com.example.task.dto.UserDTO;
import com.example.task.service.impl.EmailService;
import com.example.task.service.impl.TaskService;
import com.example.task.service.impl.UserService;
import org.springframework.expression.spel.ast.OpAnd;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.example.task.dto.constant.StatusSent.UNSENT;
import static com.example.task.dto.constant.SystemConstant.EXPIRE;

@Component
public class DeadlineAnnouncement {
    ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    TaskService taskService;
    EmailService emailService;
    UserService userService;

    public DeadlineAnnouncement(TaskService taskService, EmailService emailService, UserService userService) {
        this.userService = userService;
        this.emailService = emailService;
        this.taskService = taskService;
        scheduledExecutorService.scheduleAtFixedRate(this::job, 0, 10, TimeUnit.HOURS);
    }

    public void job() {
        try {
            List<TaskDTO> ListTaskExpire = taskService.expire();
            for (TaskDTO task : ListTaskExpire) {
                Optional<UserDTO> user = userService.findByUsername(task.getPerformer());
                if (user.isPresent()){
                    //executorService.submit(addEmail(user, task));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private Runnable addEmail(UserDTO userDTO, TaskDTO taskDTO) {
        return () -> {
            try {
                EmailDTO emailDTO = new EmailDTO();
                emailDTO.setToEmail(userDTO.getEmail());
                emailDTO.setTitle(EXPIRE + "" + taskDTO.getTitle() + "");
                emailDTO.setContent(taskDTO.getContent() + " sẽ hết thời hạn vào " + taskDTO.getDeadlineEnd() + "");
                emailDTO.setStatusSent(String.valueOf(UNSENT));
                emailService.save(emailDTO);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        };
    }
}
