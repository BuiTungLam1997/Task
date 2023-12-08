package com.example.task.controller.api.custom;

import com.example.task.dto.GroupDTO;
import com.example.task.dto.ResponseService;
import com.example.task.dto.UserDTO;
import com.example.task.service.IUserService;
import com.example.task.utils.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/admin-home")
public class AdminHomeAPI {
    private IUserService userService;

    @GetMapping(value = "/get/{username}")
    public ResponseEntity<ResponseService<UserDTO>> getOne(@PathVariable String username) {
        try {
            Optional<UserDTO> user = userService.findByUsername(username);
            return user.map(userDTO -> new ResponseEntity<>(
                            new ResponseService<>("Success", userDTO, "200"), HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(new ResponseService<>("Error!", "400"), HttpStatus.OK));
        } catch (Exception ex) {
            ResponseService<UserDTO> responseService = new ResponseService<>();
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/getListPermission")
    public ResponseEntity<ResponseService<List<GroupDTO>>> listPermission() {
        String username = SecurityUtils.getPrincipal().getUsername();
        try {
            List<GroupDTO> list = userService.getListPermission(username);
            return new ResponseEntity<>(new ResponseService<>("Success", list, "200"), HttpStatus.OK);
        } catch (Exception ex) {
            ResponseService<List<GroupDTO>> responseService = new ResponseService<>();
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
    }
}
