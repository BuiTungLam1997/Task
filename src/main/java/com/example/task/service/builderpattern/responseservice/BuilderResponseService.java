package com.example.task.service.builderpattern.responseservice;

import org.springframework.http.HttpStatus;

public interface BuilderResponseService {
    BuilderResponseService buildMessage(String message);
    BuilderResponseService buildData(Object data);
    BuilderResponseService buildStatus(HttpStatus status);

    ResponseService build();
}
