package com.example.task.controller.api;

import com.example.task.dto.CommentDTO;
import com.example.task.dto.ResponseService;
import com.example.task.dto.UserDTO;
import com.example.task.service.ICommentService;
import com.example.task.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/manager/comment")
@AllArgsConstructor
public class CommentAPI extends CommonAPI {

    private ICommentService commentService;
    private IUserService userService;

    @GetMapping(value = "/{taskId}")
    public ResponseEntity<ResponseService> listComment(@PathVariable Long taskId) {
        responseService.setMessage("Success");
        List<CommentDTO> commentDTOS;
        try {
            commentDTOS = commentService.findByTaskId(taskId);
            return new ResponseEntity<>(new ResponseService(commentDTOS, "200"), HttpStatus.OK);
        } catch (Exception ex) {
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<ResponseService> insertComment(@RequestBody CommentDTO commentDTO) {
        responseService.setMessage("Insert success");
        try {
            UserDTO user = userService.findByUsername(commentDTO.getUsername()).orElseThrow(NullPointerException::new);
            commentDTO.setUserId(user.getId());
            commentDTO = commentService.save(commentDTO);
            return new ResponseEntity<>(new ResponseService(commentDTO, "200"), HttpStatus.OK);
        } catch (Exception ex) {
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping()
    public ResponseEntity<ResponseService> updateComment(@RequestBody CommentDTO commentDTO) {
        responseService.setMessage("Update success");
        try {
            commentService.update(commentDTO);
            return ResponseEntity.ok(responseService);
        } catch (Exception ex) {
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping
    public ResponseEntity<ResponseService> deleteComment(@RequestBody CommentDTO commentDTO) {
        responseService.setMessage("Delete success");
        try {
            commentService.delete(commentDTO.getIds());
            return ResponseEntity.ok(responseService);
        } catch (Exception ex) {
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
    }
}
