package com.example.task.controller.api;

import com.example.task.dto.CommentDTO;
import com.example.task.dto.ResponseService;
import com.example.task.dto.UserDTO;
import com.example.task.service.ICommentService;
import com.example.task.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.task.dto.ResponseService.success;

@RestController
@RequestMapping(value = "/api/manager/comment")
@AllArgsConstructor
public class CommentAPI {

    private ICommentService commentService;
    private IUserService userService;

    @GetMapping(value = "/{taskId}")
    public ResponseEntity<ResponseService<List<CommentDTO>>> listComment(@PathVariable Long taskId) {
        try {
            List<CommentDTO> commentDTOS = commentService.findByTaskId(taskId);
            return new ResponseEntity<>(new ResponseService<>(commentDTOS, "200"), HttpStatus.OK);
        } catch (Exception ex) {
            ResponseService<List<CommentDTO>> responseService = new ResponseService<>();
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<ResponseService<CommentDTO>> insertComment(@RequestBody CommentDTO commentDTO) {
        try {
            UserDTO user = userService.findByUsername(commentDTO.getUsername()).orElseThrow(NullPointerException::new);
            commentDTO.setUserId(user.getId());
            commentDTO = commentService.save(commentDTO);
            return new ResponseEntity<>(new ResponseService<>(commentDTO, "200"), HttpStatus.OK);
        } catch (Exception ex) {
            ResponseService<CommentDTO> responseService = new ResponseService<>();
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping()
    public ResponseEntity<ResponseService<CommentDTO>> updateComment(@RequestBody CommentDTO commentDTO) {
        try {
            commentService.update(commentDTO);
            success();
        } catch (Exception ex) {
            ResponseService<CommentDTO> responseService = new ResponseService<>();
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    @DeleteMapping
    public ResponseEntity<ResponseService<CommentDTO>> deleteComment(@RequestBody CommentDTO commentDTO) {
        try {
            commentService.delete(commentDTO.getIds());
            success();
        } catch (Exception ex) {
            ResponseService<CommentDTO> responseService = new ResponseService<>();
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
        return null;
    }
}
