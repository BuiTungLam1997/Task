package com.example.task.api;

import com.example.task.dto.UserDTO;
import com.example.task.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@RequiredArgsConstructor
@RestController(value = "apiOfUser")
@RequestMapping(value = "/api/user")
public class UserAPI {
    private final IUserService userService;


    @PostMapping
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        userDTO = userService.save(userDTO);
        return userDTO;
    }

    @PutMapping
    public UserDTO updateUser(@RequestBody UserDTO userDTO) {
        return userService.update(userDTO);
    }

    @DeleteMapping
    public void deleteUser(@RequestBody UserDTO userDTO) {
        userService.deleteUser(userDTO.getIds());
    }
}
