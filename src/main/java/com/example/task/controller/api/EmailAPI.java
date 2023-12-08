package com.example.task.controller.api;

import com.example.task.dto.EmailDTO;
import com.example.task.dto.ResponseService;
import com.example.task.service.IEmailService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.task.dto.ResponseService.success;

@RestController
@RequestMapping(value = "/api-email")
@AllArgsConstructor
public class EmailAPI {
    private IEmailService emailService;

    @PostMapping
    public ResponseEntity<ResponseService<EmailDTO>> createEmail(@RequestBody EmailDTO emailDTO) {
        try {
            emailService.save(emailDTO);
            success();
        } catch (Exception ex) {
            ResponseService<EmailDTO> responseService = new ResponseService<>();
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    @PutMapping
    public ResponseEntity<ResponseService<EmailDTO>> updateEmail(@RequestBody EmailDTO emailDTO) {
        try {
            emailService.update(emailDTO);
            success();
        } catch (Exception ex) {
            ResponseService<EmailDTO> responseService = new ResponseService<>();
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
        return null;
    }
}
