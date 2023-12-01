package com.example.task.controller.views.admin;

import com.example.task.dto.CommentDTO;
import com.example.task.dto.TaskDTO;
import com.example.task.dto.UserDTO;
import com.example.task.service.ICommentService;
import com.example.task.service.ITaskService;
import com.example.task.service.IUserService;
import com.example.task.utils.MessageUtils;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class CommentController {

    private ITaskService taskService;

    private IUserService userService;

    private ICommentService commentService;

    private MessageUtils messageUtils;

    @GetMapping(value = "/comment")
    public ModelAndView getComment(@RequestParam(required = false) Long id,
                                   @RequestParam(required = false) String message) {

        ModelAndView mav = new ModelAndView("/admin/task/comment");
        Optional<TaskDTO> taskDTO = taskService.findById(id);
        if (!taskDTO.isPresent()) {
            return mav;
        }
        TaskDTO task = taskDTO.get();
        task.setLevelOfDifficulty(unFibonaci(task.getPoint()));
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

    private int unFibonaci(int point) {
        if (point <= 1) return 1;
        else {
            int i = 2;
            while (fib(i) != point) {
                i++;
            }
            return i;
        }
    }

    private int fib(int n) {
        if (n < 2) return 1;
        else return fib(n - 1) + fib(n - 2);
    }
}
