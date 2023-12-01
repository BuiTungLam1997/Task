package com.example.task.controller.api;

import com.example.task.dto.PermissionGroupDTO;
import com.example.task.dto.ResponseService;
import com.example.task.service.IPermissionGroupService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.task.dto.constant.Pageable.defaultLimit;
import static com.example.task.dto.constant.Pageable.defaultPage;

@RestController
@RequestMapping(value = "/api/permission-group")
@AllArgsConstructor
public class PermissionGroupAPI extends CommonAPI {
    private IPermissionGroupService permissionGroupService;

    @PostMapping(value = "/create")
    public ResponseEntity<ResponseService> createPermissionGroup(@RequestBody PermissionGroupDTO permissionGroupDTO) {
        try {
            permissionGroupService.save(permissionGroupDTO);
            return new ResponseEntity<>(new ResponseService("success", "200"), HttpStatus.OK);
        } catch (Exception ex) {
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<ResponseService> updatePermissionGroup(@RequestBody PermissionGroupDTO permissionGroupDTO) {
        try {
            permissionGroupService.update(permissionGroupDTO);
            return new ResponseEntity<>(new ResponseService("sucscess", "200"), HttpStatus.OK);
        } catch (Exception ex) {
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping
    public ResponseEntity<ResponseService> deletePermissionGroup(@RequestBody PermissionGroupDTO permissionGroupDTO) {
        try {
            permissionGroupService.delete(permissionGroupDTO);
            return new ResponseEntity<>(new ResponseService("succses", "200"), HttpStatus.OK);
        } catch (Exception ex) {
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/list")
    public ResponseEntity<ResponseService> listPermissionGroup(@RequestBody PermissionGroupDTO permissionGroupDTO,
                                                               @RequestParam(required = false, defaultValue = "" + defaultPage + "") Integer page,
                                                               @RequestParam(required = false, defaultValue = "" + defaultLimit + "") Integer limit) {
        try {
            Pageable pageable = PageRequest.of(page - 1, limit);
            Page<PermissionGroupDTO> listResult = permissionGroupService.query(pageable);
            List<PermissionGroupDTO> listData = permissionGroupService.findAll(pageable);
            int totalPage = listResult.getTotalPages();
            return new ResponseEntity<>(new ResponseService(listData, totalPage, page), HttpStatus.OK);
        } catch (Exception ex) {
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
    }
}
