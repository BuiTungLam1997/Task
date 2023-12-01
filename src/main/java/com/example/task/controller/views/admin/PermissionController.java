package com.example.task.controller.views.admin;

import ch.qos.logback.core.net.SyslogConstants;
import com.example.task.dto.GroupDTO;
import com.example.task.dto.PermissionDTO;
import com.example.task.dto.constant.SystemConstant;
import com.example.task.service.IGroupService;
import com.example.task.service.IPermissionService;
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
public class PermissionController {

    private IPermissionService permissionService;
    private IGroupService groupService;
    private MessageUtils messageUtils;


    @GetMapping(value = "/admin-permission-list")
    public ModelAndView listPermission(@RequestParam(required = false) String message) {
        ModelAndView mav = new ModelAndView("/admin/permission/list");
        if (message != null) {
            addMessage(mav, message);
        }
        return mav;
    }

    @GetMapping(value = "/admin-permission-edit")
    public ModelAndView editPermission(@RequestParam(value = "id", required = false) Long id,
                                       @RequestParam(value = "message", required = false) String message) {

        ModelAndView mav = new ModelAndView("/admin/permission/edit");
        Optional<PermissionDTO> permissionDTO;
        permissionDTO = (id != null) ? permissionService.findById(id) : Optional.empty();
        PermissionDTO permission = permissionDTO.orElseThrow(NullPointerException::new);
        mav.addObject(model, permission);
        return mav;
    }

    private void addMessage(ModelAndView mav, String message) {
        Map<String, String> result = messageUtils.getMessage(message);
        mav.addObject(String.valueOf(MESSAGE), result.get(String.valueOf(MESSAGE)));
        mav.addObject(String.valueOf(ALERT), result.get(String.valueOf(ALERT)));
    }
}
