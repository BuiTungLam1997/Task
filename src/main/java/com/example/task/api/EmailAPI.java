package com.example.task.api;

import com.example.task.dto.EmailDTO;
import com.example.task.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/email")
public class EmailAPI {
    @Autowired
    private IEmailService emailService;
    @PostMapping
    public EmailDTO createEmail(@RequestBody EmailDTO emailDTO){
        return emailService.save(emailDTO);
    }
    @PutMapping
    public void updateEmail(@RequestBody EmailDTO emailDTO){
         emailService.update(emailDTO);
    }
}
