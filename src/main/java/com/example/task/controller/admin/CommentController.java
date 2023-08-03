package com.example.task.controller.admin;

import com.example.task.dto.CommentDTO;
import com.example.task.dto.TaskDTO;
import com.example.task.dto.UserDTO;
import com.example.task.service.ICommentService;
import com.example.task.service.ITaskService;
import com.example.task.service.IUserService;
import com.example.task.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.Optional;

import static com.example.task.dto.constant.StatusMessage.ALERT;
import static com.example.task.dto.constant.StatusMessage.MESSAGE;

@Controller
public class CommentController {
    @Autowired
    private ITaskService taskService;
    @Autowired
    private IUserService userService;
    @Autowired
    private ICommentService commentService;
    @Autowired
    private MessageUtils messageUtils;

    @GetMapping(value = "/comment")
    public ModelAndView getComment(@RequestParam(value = "id", required = false) Long id,
                                   @RequestParam(value = "message", required = false) String message) {

        ModelAndView mav = new ModelAndView("/admin/task/comment");
        Optional<TaskDTO> taskDTO = taskService.findById(id);
        if (!taskDTO.isPresent()) {
            return mav;
        }
        TaskDTO task = taskDTO.get();
        mav.addObject("modelTask", task);
        Optional<UserDTO> userDTO = userService.findByUsername(task.getPerformer());
        if (userDTO.isPresent()) {
            mav.addObject("modelUser", userDTO);
        }

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setListResult(commentService.setListResult(task.getId()));
        if (commentDTO.getListResult() != null) {
            commentDTO.setTotalComment(commentService.countByTaskId(task.getId()));
            mav.addObject("modelComment", commentDTO);
        }

        if (message != null) {
            addMessage(mav, message);
        }
        return mav;
    }

    private void addMessage(ModelAndView mav, String message) {
        Map<String, String> result = messageUtils.getMessage(message);
        mav.addObject(String.valueOf(MESSAGE), result.get(String.valueOf(MESSAGE)));
        mav.addObject(String.valueOf(ALERT), result.get(String.valueOf(ALERT)));
    }
}
