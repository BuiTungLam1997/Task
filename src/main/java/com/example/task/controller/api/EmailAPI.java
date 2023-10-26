package com.example.task.controller.api;

import com.example.task.dto.EmailDTO;
import com.example.task.dto.ResponseService;
import com.example.task.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api-email")
public class EmailAPI extends CommonAPI {
    @Autowired
    private IEmailService emailService;

    @PostMapping
    public ResponseEntity<ResponseService> createEmail(@RequestBody EmailDTO emailDTO) {
        responseService.setMessage("Insert success");
        try {
            emailService.save(emailDTO);
        } catch (Exception ex) {
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(responseService);
    }

    @PutMapping
    public ResponseEntity<ResponseService> updateEmail(@RequestBody EmailDTO emailDTO) {
        responseService.setMessage("Update success");
        try {
            emailService.update(emailDTO);
        } catch (Exception ex) {
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(responseService);
    }
}
