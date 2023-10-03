package com.example.task;

import com.example.task.service.threads.ThreadSendEmail;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
@EnableScheduling
public class TaskApplication {
    private static final int NUM_OF_THREAD = 5;

    public static void main(String[] args) {
        SpringApplication.run(TaskApplication.class, args);

        ExecutorService executorService = Executors.newFixedThreadPool(NUM_OF_THREAD);
        Runnable runnable = new ThreadSendEmail();
        executorService.execute(runnable);
    }

}
