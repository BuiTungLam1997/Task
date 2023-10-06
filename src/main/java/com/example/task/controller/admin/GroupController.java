package com.example.task.controller.admin;

import com.example.task.dto.GroupDTO;
import com.example.task.dto.UserDTO;
import com.example.task.service.IGroupService;
import com.example.task.service.IUserService;
import com.example.task.utils.MessageUtils;
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

import static com.example.task.dto.constant.StatusMessage.ALERT;
import static com.example.task.dto.constant.StatusMessage.MESSAGE;
import static com.example.task.dto.constant.SystemConstant.model;

@Controller
public class GroupController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IGroupService groupService;
    @Autowired
    private MessageUtils messageUtils;


    @GetMapping(value = "/admin-group-list")
    public ModelAndView listPermission(@ModelAttribute("model") GroupDTO groupDTO,
                                       @RequestParam(value = "page", required = false) Integer page,
                                       @RequestParam(value = "limit", required = false) Integer limit,
                                       @RequestParam(value = "message", required = false) String message) {
        ModelAndView mav = new ModelAndView("/admin/group/list");

        if (page == null || limit == null) {
            page = 1;
            limit = 4;
        }
        Pageable pageable = PageRequest.of(page - 1, limit);

        Page<GroupDTO> pageResult = groupService.findAllPage(pageable);
        groupDTO.setListResult(pageResult.getContent());
        groupDTO.setPage(page);

        groupDTO.setLimit(limit);
        groupDTO.setTotalItem((int) pageResult.getTotalElements());
        groupDTO.setTotalPage(pageResult.getTotalPages());

        if (message != null) {
            addMessage(mav, message);
        }

        mav.addObject(model, groupDTO);
        return mav;
    }

    @GetMapping(value = "/admin-group-edit")
    public ModelAndView editPermission(@RequestParam(value = "id", required = false) Long id,
                                       @RequestParam(value = "message", required = false) String message) {

        ModelAndView mav = new ModelAndView("/admin/group/edit");
        GroupDTO groupDTO = new GroupDTO();
        groupDTO = (id != null) ? groupService.findById(id) : groupDTO;
        mav.addObject(model, groupDTO);
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
