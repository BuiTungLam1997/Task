package com.example.task.controller.admin;

import com.example.task.dto.GroupDTO;
import com.example.task.dto.PermissionDTO;
import com.example.task.service.IGroupService;
import com.example.task.service.IPermissionService;
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
public class PermissionController {
    @Autowired
    private IPermissionService permissionService;
    @Autowired
    private IGroupService groupService;
    @Autowired
    private MessageUtils messageUtils;

    @GetMapping(value = "/admin-permission-list")
    public ModelAndView listPermission(@ModelAttribute("model") PermissionDTO permissionDTO,
                                       @RequestParam(value = "page", required = false) Integer page,
                                       @RequestParam(value = "limit", required = false) Integer limit,
                                       @RequestParam(value = "message", required = false) String message) {
        ModelAndView mav = new ModelAndView("/admin/permission/list");

        if (page == null || limit == null) {
            page = 1;
            limit = 4;
        }
        Pageable pageable = PageRequest.of(page - 1, limit);

        Page<PermissionDTO> pageResult = permissionService.findAllPage(pageable);
        permissionDTO.setListResult(pageResult.getContent());
        permissionDTO.setPage(page);

        permissionDTO.setLimit(limit);
        permissionDTO.setTotalItem((int) pageResult.getTotalElements());
        permissionDTO.setTotalPage(pageResult.getTotalPages());

        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setListResult(groupService.findAll());

        if (message != null) {
            addMessage(mav, message);
        }

        mav.addObject("modelGroup", groupDTO);
        mav.addObject(model, permissionDTO);
        return mav;
    }

    @GetMapping(value = "/admin-permission-edit")
    public ModelAndView editPermission(@RequestParam(value = "id", required = false) Long id,
                                       @RequestParam(value = "message", required = false) String message) {

        ModelAndView mav = new ModelAndView("/admin/permission/edit");
        Object permissionDTO = new PermissionDTO();
        permissionDTO = (id != null) ? permissionService.findById(id) : permissionDTO;
        mav.addObject(model, permissionDTO);
        return mav;
    }

    private void addMessage(ModelAndView mav, String message) {
        Map<String, String> result = messageUtils.getMessage(message);
        mav.addObject(String.valueOf(MESSAGE), result.get(String.valueOf(MESSAGE)));
        mav.addObject(String.valueOf(ALERT), result.get(String.valueOf(ALERT)));
    }
}
