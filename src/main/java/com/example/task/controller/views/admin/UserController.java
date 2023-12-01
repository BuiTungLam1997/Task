package com.example.task.controller.views.admin;

import com.example.task.dto.GroupDTO;
import com.example.task.dto.UserDTO;
import com.example.task.service.IGroupService;
import com.example.task.service.IUserService;
import com.example.task.utils.MessageUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static com.example.task.dto.constant.Pageable.defaultLimit;
import static com.example.task.dto.constant.Pageable.defaultPage;
import static com.example.task.dto.constant.StatusMessage.ALERT;
import static com.example.task.dto.constant.StatusMessage.MESSAGE;
import static com.example.task.dto.constant.SystemConstant.model;

@Controller
@AllArgsConstructor
public class UserController {
    private IUserService userService;
    private MessageUtils messageUtils;
    private PasswordEncoder passwordEncoder;
    private IGroupService groupService;

    @GetMapping(value = {"/admin-user-list"})

    public ModelAndView homeUserList() {
        ModelAndView mav = new ModelAndView("/admin/user/list");
        List<GroupDTO> listGroup = groupService.findAll();
        mav.addObject("listGroup", listGroup);
        return mav;
    }

    @GetMapping(value = "/admin-user-edit")
    public ModelAndView newsEdit(@RequestParam(value = "id", required = false) Long id) {

        ModelAndView mav = new ModelAndView("/admin/user/edit");
        mav.addObject("id", id);
        return mav;
    }

    @GetMapping(value = "/admin-user-create")
    public ModelAndView createUser() {
        return new ModelAndView("/admin/user/create");
    }

    @GetMapping(value = "/admin-change-password-edit")
    public ModelAndView changePasswordEdit() {
        return new ModelAndView("/admin/user/change");
    }

    @GetMapping(value = "/admin-search-user")
    public ModelAndView searchTask(
            @RequestParam(value = "search", required = false) String search
    ) {
        if (Objects.equals(search, "")) {
            return new ModelAndView("redirect:/admin-task-list");
        }
        ModelAndView mav = new ModelAndView("/admin/user/search");
        List<GroupDTO> listGroup = groupService.findAll();
        mav.addObject("listGroup", listGroup);
        mav.addObject("searchResponse", search);
        return mav;
    }

    @GetMapping(value = "/admin-change-password")
    public ModelAndView changePassword() {
        return new ModelAndView("/admin/user/change");
    }

    private boolean checkPassword(UserDTO userDTO) {
        Optional<UserDTO> user = userService.findByUsername(userDTO.getUsername());
        if (user.isPresent()) {
            String pass = user.get().getPassword();
            return passwordEncoder.matches(userDTO.getPassword(), pass);
        }
        return false;
    }

    private boolean checkPasswordNewAndRetype(UserDTO userDTO) {
        return userDTO.getNewPassword().equals(userDTO.getRetypePassword());
    }

    private void addMessage(ModelAndView mav, String message) {
        Map<String, String> result = messageUtils.getMessage(message);
        mav.addObject(String.valueOf(MESSAGE), result.get(String.valueOf(MESSAGE)));
        mav.addObject(String.valueOf(ALERT), result.get(String.valueOf(ALERT)));
    }

}
