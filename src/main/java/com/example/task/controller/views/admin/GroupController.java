package com.example.task.controller.views.admin;

import com.example.task.dto.GroupDTO;
import com.example.task.dto.UserDTO;
import com.example.task.dto.constant.SystemConstant;
import com.example.task.service.IGroupService;
import com.example.task.service.IUserService;
import com.example.task.utils.MessageUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.Optional;

import static com.example.task.dto.constant.Pageable.defaultLimit;
import static com.example.task.dto.constant.Pageable.defaultPage;
import static com.example.task.dto.constant.StatusMessage.ALERT;
import static com.example.task.dto.constant.StatusMessage.MESSAGE;
import static com.example.task.dto.constant.SystemConstant.model;

@Controller
@AllArgsConstructor
public class GroupController {

    private IUserService userService;
    private IGroupService groupService;
    private MessageUtils messageUtils;

    @GetMapping(value = "/admin-group-list")
    public ModelAndView listPermission(@ModelAttribute GroupDTO model,
                                       @RequestParam(required = false, defaultValue = "" + defaultPage + "") Integer page,
                                       @RequestParam(required = false, defaultValue = "" + defaultLimit + "") Integer limit,
                                       @RequestParam(required = false) String message) {
        ModelAndView mav = new ModelAndView("/admin/group/list");
        Pageable pageable = PageRequest.of(page - 1, limit);

        Page<GroupDTO> pageResult = groupService.query(pageable);
        model.setListResult(pageResult.getContent());
        model.setPage(page);

        model.setLimit(limit);
        model.setTotalItem((int) pageResult.getTotalElements());
        model.setTotalPage(pageResult.getTotalPages());

        if (message != null) {
            addMessage(mav, message);
        }

        mav.addObject(SystemConstant.model, model);
        return mav;
    }

    @GetMapping(value = "/admin-group-edit")
    public ModelAndView editPermission(@RequestParam(value = "id", required = false) Long id,
                                       @RequestParam(value = "message", required = false) String message) {

        ModelAndView mav = new ModelAndView("/admin/group/edit");
        Optional<GroupDTO> groupDTO;
        groupDTO = (id != null) ? groupService.findById(id) : Optional.empty();
        GroupDTO group = groupDTO.orElseThrow(NullPointerException::new);
        mav.addObject(model, group);
        if (message != null) {
            addMessage(mav, message);
        }
        return mav;
    }

    @GetMapping(value = "/admin-group-user-list")
    public ModelAndView groupAdminList(@ModelAttribute("model") UserDTO userDTO) {
        ModelAndView mav = new ModelAndView("/admin/group/user/list");
        UserDTO listAdminUser = new UserDTO();
        listAdminUser.setListResult(userService.findByAdminUser());
        mav.addObject("model", listAdminUser);
        return mav;
    }

    @GetMapping(value = "/admin-group-task-list")
    public ModelAndView groupTaskList(@ModelAttribute("model") UserDTO userDTO) {
        ModelAndView mav = new ModelAndView("/admin/group/task/list");
        UserDTO listAdminTask = new UserDTO();
        listAdminTask.setListResult(userService.findByAdminTask());
        mav.addObject("model", listAdminTask);
        return mav;
    }

    private void addMessage(ModelAndView mav, String message) {
        Map<String, String> result = messageUtils.getMessage(message);
        mav.addObject(String.valueOf(MESSAGE), result.get(String.valueOf(MESSAGE)));
        mav.addObject(String.valueOf(ALERT), result.get(String.valueOf(ALERT)));
    }
}
