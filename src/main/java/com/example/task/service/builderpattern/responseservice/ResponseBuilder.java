package com.example.task.service.builderpattern.responseservice;

import org.springframework.http.HttpStatus;

public class ResponseBuilder implements BuilderResponseService {
    private String message;
    private Object data;
    private HttpStatus status;

    @Override
    public BuilderResponseService buildMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public BuilderResponseService buildData(Object data) {
        this.data = data;
        return this;
    }

    @Override
    public BuilderResponseService buildStatus(HttpStatus status) {
        this.status = status;
        return null;
    }

    @Override
    public ResponseService build() {
        return new ResponseService(message, data, status);
    }
}
