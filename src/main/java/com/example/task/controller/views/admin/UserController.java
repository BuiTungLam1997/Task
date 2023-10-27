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

    public ModelAndView homeUserList(@ModelAttribute("model") UserDTO userDTO,
                                     @RequestParam(value = "page", required = false) Integer page,
                                     @RequestParam(value = "limit", required = false) Integer limit,
                                     @RequestParam(value = "message", required = false) String message) {
        ModelAndView mav = new ModelAndView("/admin/user/list");
        if ((page == null || limit == null)) {
            page = defaultPage;
            limit = defaultLimit;
        }
        Pageable pageable = PageRequest.of(page - 1, limit);

        Page<UserDTO> pageResult = userService.query(pageable);
        userDTO.setPage(page);
        userDTO.setLimit(limit);
        userDTO.setListResult(pageResult.getContent());
        userDTO.setTotalItem((int) pageResult.getTotalElements());
        userDTO.setTotalPage(pageResult.getTotalPages());

        if (message != null) {
            addMessage(mav, message);
        }
        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setListResult(groupService.findAll());
        mav.addObject("modelGroup", groupDTO);
        mav.addObject(model, userDTO);
        return mav;
    }

    @GetMapping(value = "/admin-user-edit")
    public ModelAndView newsEdit(@RequestParam(value = "id", required = false) Long id,
                                 @RequestParam(value = "message", required = false) String message) {

        ModelAndView mav = new ModelAndView("/admin/user/edit");
        Optional<UserDTO> userDTO;
        userDTO = (id != null) ? userService.findById(id) : Optional.empty();
        UserDTO user = userDTO.orElseThrow(NullPointerException::new);
        mav.addObject(model, user);
        if (message != null) {
            addMessage(mav, message);
        }
        return mav;
    }

    @GetMapping(value = "/admin-user-create")
    public ModelAndView createUser() {
        return new ModelAndView("/admin/user/create");
    }

    @GetMapping(value = "/admin-change-password-edit")
    public ModelAndView changePasswordEdit(@RequestParam(value = "id", required = false) Long id,
                                           @ModelAttribute("model") UserDTO userDTO) {
        ModelAndView mav = new ModelAndView("/admin/user/change");
        if (!checkPassword(userDTO)) {
            String message = "Mật khẩu bạn vừa nhập không đúng ,mười bạn nhập lại";
            String alert = "danger";
            mav.addObject(String.valueOf(MESSAGE), message);
            mav.addObject(String.valueOf(ALERT), alert);
            return mav;
        }
        if (!checkPasswordNewAndRetype(userDTO)) {
            String message = "Mật khẩu bạn vừa nhập lại không đúng ,mười bạn nhập lại";
            String alert = "danger";
            mav.addObject(String.valueOf(MESSAGE), message);
            mav.addObject(String.valueOf(ALERT), alert);
            return mav;
        }
        userService.changePassword(userDTO);
        mav.addObject(String.valueOf(MESSAGE), "Thay đổi thành công");
        mav.addObject(String.valueOf(ALERT), "success");
        return new ModelAndView("redirect:/dang-nhap");
    }

    @GetMapping(value = "/admin-search-user")
    public ModelAndView searchTask(@RequestParam(value = "search", required = false) String search,
                                   @RequestParam(value = "searchTws", required = false) String searchTws,
                                   @RequestParam(value = "page", required = false) Integer page,
                                   @RequestParam(value = "limit", required = false) Integer limit,
                                   @RequestParam(value = "message", required = false) String message) {
        if (Objects.equals(search, "")) {
            return new ModelAndView("redirect:/admin-task-list");
        }
        if (page == null || limit == null) {
            page = defaultPage;
            limit = defaultLimit;
        }
        Pageable pageable = PageRequest.of(page - 1, limit);

        if (search.isEmpty()) {
            search = searchTws;
        }
        Page<UserDTO> pageResult = userService.querySearch(search, pageable);

        UserDTO userDTO = new UserDTO();
        userDTO.setPage(page);
        userDTO.setLimit(limit);
        userDTO.setListResult(pageResult.getContent());

        userDTO.setTotalItem((int) pageResult.getTotalElements());
        userDTO.setTotalPage(pageResult.getTotalPages());
        userDTO.setSearchResponse(search);

        ModelAndView mav = new ModelAndView("/admin/user/search");
        mav.addObject(model, userDTO);
        return mav;
    }

    @GetMapping(value = "/admin-change-password")
    public ModelAndView changePassword() {
        return new ModelAndView("/admin/user/change");
    }

    private boolean checkPassword(UserDTO userDTO) {
        UserDTO user = userService.findByUsername(userDTO.getUsername());
        if (user != null) {
            String pass = user.getPassword();
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
