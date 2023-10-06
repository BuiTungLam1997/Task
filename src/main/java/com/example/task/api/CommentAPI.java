package com.example.task.api;

import com.example.task.dto.CommentDTO;
import com.example.task.dto.UserDTO;
import com.example.task.service.ICommentService;
import com.example.task.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/manager/comment")
public class CommentAPI {
    @Autowired
    private ICommentService commentService;
    @Autowired
    private IUserService userService;

    @GetMapping
    public List<CommentDTO> findAll() {
        return commentService.findAll();
    }

    @PostMapping
    public CommentDTO insertComment(@RequestBody CommentDTO commentDTO) {
        Optional<UserDTO> user = userService.findByUsername(commentDTO.getUsername());
        if (user.isPresent()) {
            UserDTO userDTO = user.get();
            commentDTO.setUserId(userDTO.getId());
            return commentService.save(commentDTO);
        }
        return null;
    }

    @PutMapping(value = "/{id}")
    public CommentDTO updateComment(@RequestBody CommentDTO commentDTO,@PathVariable Long id){
        if (id == null) {
            return commentService.save(commentDTO);
        }
        commentDTO.setId(id);
        return commentService.update(commentDTO);
    }

    @DeleteMapping
    public void deleteComment(@RequestBody CommentDTO commentDTO) {
        commentService.delete(commentDTO.getIds());
    }
}
