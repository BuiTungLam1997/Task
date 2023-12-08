package com.example.task.exception;

public class WrongPasswordException extends RuntimeException{
    private int code;
    private String name;

    public WrongPasswordException() {
        super("Sai mat khau");
    }
}
