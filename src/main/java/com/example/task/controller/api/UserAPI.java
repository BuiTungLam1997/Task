package com.example.task.controller.api;

import com.example.task.dto.GroupDTO;
import com.example.task.dto.ResponseService;
import com.example.task.dto.UserDTO;
import com.example.task.service.IGroupService;
import com.example.task.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController(value = "apiOfUser")
@RequestMapping(value = "/api/user")
public class UserAPI extends CommonAPI {
    private final IUserService userService;

    private final IGroupService groupService;

    @GetMapping(value = "/list-group/{id}")
    public ResponseEntity<ResponseService> listGroupByUser(@PathVariable Long id) {
        responseService.setMessage("Success");
        List<GroupDTO> data = new ArrayList<>();
        try {
            data = groupService.findByUserId(id);
        } catch (Exception ex) {
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ResponseService(data, "200"), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseService> createUser(@RequestBody UserDTO userDTO) {
        responseService.setMessage("Insert success");
        try {
            userService.save(userDTO);
        } catch (Exception ex) {
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(responseService);
    }

    @PutMapping
    public ResponseEntity<ResponseService> updateUser(@RequestBody UserDTO userDTO) {
        responseService.setMessage("Update success");
        try {
            userService.update(userDTO);
        } catch (Exception e) {
            responseService.setMessage(e.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(responseService);
    }

    @DeleteMapping
    public ResponseEntity<ResponseService> deleteUser(@RequestBody UserDTO userDTO) {
        responseService.setMessage("Delete success");
        try {
            userService.deleteUser(userDTO.getIds());

        } catch (Exception ex) {
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(responseService);
    }
}
