package com.example.task;

import com.example.task.service.threads.ThreadSendEmail;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaskApplication {
	public static void main(String[] args) {
		SpringApplication.run(TaskApplication.class, args);
		ThreadSendEmail threadSendEmail = new ThreadSendEmail();
		threadSendEmail.start();
	}

}
