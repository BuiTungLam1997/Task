package com.example.task.controller.api;

import com.example.task.dto.GroupDTO;
import com.example.task.dto.ResponseService;
import com.example.task.dto.UserDTO;
import com.example.task.service.IGroupService;
import com.example.task.service.IUserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.task.dto.constant.Pageable.defaultLimit;
import static com.example.task.dto.constant.Pageable.defaultPage;

@RestController(value = "apiOfUser")
@RequestMapping(value = "/api/user")
@AllArgsConstructor
public class UserAPI extends CommonAPI {
    private final IUserService userService;
    private final IGroupService groupService;
    private PasswordEncoder passwordEncoder;

    @GetMapping(value = "/list-group/{id}")
    public ResponseEntity<ResponseService> listGroupByUser(@PathVariable Long id) {
        responseService.setMessage("Success");
        List<GroupDTO> data;
        try {
            data = groupService.findByUserId(id);
            return new ResponseEntity<>(new ResponseService(data, "200"), HttpStatus.OK);
        } catch (Exception ex) {
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping(value = "/create")
    public ResponseEntity<ResponseService> createUser(@RequestBody UserDTO userDTO) {
        responseService.setMessage("Insert success");
        try {
            userService.save(userDTO);
            return ResponseEntity.ok(responseService);
        } catch (Exception ex) {
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping
    public ResponseEntity<ResponseService> updateUser(@RequestBody UserDTO userDTO) {
        responseService.setMessage("Update success");
        try {
            userService.update(userDTO);
            return ResponseEntity.ok(responseService);
        } catch (Exception e) {
            responseService.setMessage(e.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping(value = "/change")
    public ResponseEntity<ResponseService> change(@RequestBody UserDTO userDTO) {
        responseService.setMessage("Success");
        try {
            if (!checkPassword(userDTO)) {
                responseService.setMessage("Mật khẩu cũ bạn nhập vào không đúng ,mời bạn nhập lại");
                responseService.setStatus("400");
                return new ResponseEntity<>(new ResponseService(responseService.getMessage(), responseService.getStatus()), HttpStatus.OK);
            } else if (!checkPasswordNewAndRetype(userDTO)) {
                responseService.setMessage("Mật khẩu mơời bạn vừa nhập lại không giống nhau ,mời bạn nhập lại mật khẩu mới");
                responseService.setStatus("400");
                return new ResponseEntity<>(new ResponseService(responseService.getMessage(), responseService.getStatus()), HttpStatus.OK);
            }
            userService.changePassword(userDTO);
            responseService.setMessage("Thay đổi mật khẩu thành công!");
            responseService.setStatus("200");
            return new ResponseEntity<>(new ResponseService(responseService.getMessage(), responseService.getStatus()), HttpStatus.OK);

        } catch (Exception ex) {
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping
    public ResponseEntity<ResponseService> deleteUser(@RequestBody UserDTO userDTO) {
        responseService.setMessage("Delete success");
        try {
            userService.deleteUser(userDTO.getIds());
            return ResponseEntity.ok(responseService);
        } catch (Exception ex) {
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/list")
    public ResponseEntity<ResponseService> list(
            @RequestParam(required = false, defaultValue = "" + defaultPage + "") Integer page,
            @RequestParam(required = false, defaultValue = "" + defaultLimit + "") Integer limit) {
        responseService.setMessage("Success");
        try {
            Pageable pageable = PageRequest.of(page - 1, limit);
            Page<UserDTO> users = userService.query(pageable);
            if (users.getSize() == 0) {
                responseService.setMessage("Có lỗi xảy ra hoặc dữ liệu bạn yêu cầu không có ,hãy kiểm tra lại");
                responseService.setStatus("400");
                return new ResponseEntity<>(
                        new ResponseService(responseService.getMessage(), responseService.getStatus()), HttpStatus.OK);
            }
            return new ResponseEntity<>(new ResponseService(responseService.getMessage(), users.getContent(),
                    users.getTotalPages(), page, limit, "200"), HttpStatus.OK);

        } catch (Exception ex) {
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/get/{id}")
    public ResponseEntity<ResponseService> getOne(@PathVariable Long id) {
        responseService.setMessage("Success");
        try {
            Optional<UserDTO> user = userService.findById(id);
            return user.map(userDTO -> new ResponseEntity<>(new ResponseService(responseService.getMessage(), userDTO, "200"), HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(new ResponseService("Co loi da xay ra ,kiem tra lai", "400"), HttpStatus.OK));
        } catch (Exception exception) {
            responseService.setMessage(exception.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/search/{search}")
    public ResponseEntity<ResponseService> search(
            @PathVariable String search,
            @RequestParam(required = false, defaultValue = "" + defaultPage + "") Integer page,
            @RequestParam(required = false, defaultValue = "" + defaultLimit + "") Integer limit
    ) {
        responseService.setMessage("Success");
        try {
            Pageable pageable = PageRequest.of(page - 1, limit);
            Page<UserDTO> user = userService.querySearch(search, pageable);
            if (!user.isEmpty()) {
                return new ResponseEntity<>(
                        new ResponseService(responseService.getMessage(), user.getContent(),
                                user.getTotalPages(), page, limit, "200")
                        , HttpStatus.OK);
            } else {
                responseService.setMessage("Không tìm thấy thông tin user");
                responseService.setStatus("400");
                return new ResponseEntity<>(
                        new ResponseService(responseService.getMessage(), responseService.getStatus()), HttpStatus.OK);
            }

        } catch (Exception ex) {
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
    }

    private boolean checkPassword(UserDTO userDTO) {
        Optional<UserDTO> user = userService.findByUsername(userDTO.getUsername());
        if (user.isPresent()) {
            String oldPass = user.get().getPassword();
            String newPass = userDTO.getPassword();
            return passwordEncoder.matches(newPass, oldPass);
        }
        return false;
    }

    private boolean checkPasswordNewAndRetype(UserDTO userDTO) {
        return userDTO.getNewPassword().equals(userDTO.getRetypePassword());
    }
}
