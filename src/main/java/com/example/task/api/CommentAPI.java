package com.example.task.api;

import com.example.task.dto.CommentDTO;
import com.example.task.service.ICommentService;
import com.example.task.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/manager-api-comment")
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
        Long userId = userService.findByUsername(commentDTO.getUsername()).get().getId();
        commentDTO.setUserId(userId);
        return commentService.save(commentDTO);
    }
    @PutMapping(value = "/{id}")
    public CommentDTO updateComment(@RequestBody CommentDTO commentDTO,@PathVariable Long id){
        if(id==null){
            return commentService.save(commentDTO);
        }
        commentDTO.setId(id);
        return commentService.update(commentDTO);
    }
    @DeleteMapping
    public void deleteComment(@RequestBody Long[] ids){
       commentService.delete(ids);
    }
}
