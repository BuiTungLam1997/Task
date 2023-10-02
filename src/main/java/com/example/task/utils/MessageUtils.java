package com.example.task.utils;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.example.task.dto.constant.StatusMessage.*;

@Component
public class MessageUtils {
    public Map<String, String> getMessage(String message) {
        Map<String, String> result = new HashMap<>();
        switch (message) {
            case "insert_success":
                result.put(String.valueOf(MESSAGE), "Insert success");
                result.put(String.valueOf(ALERT), "success");
                break;
            case "update_success":
                result.put(String.valueOf(MESSAGE), "Update success");
                result.put(String.valueOf(ALERT), "success");
                break;
            case "error_system":
                result.put(String.valueOf(MESSAGE), "Error !");
                result.put(String.valueOf(ALERT), "danger");
                break;
        }
        return result;
    }
}
