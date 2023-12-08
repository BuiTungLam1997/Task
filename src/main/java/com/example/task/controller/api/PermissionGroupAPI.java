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

import static com.example.task.dto.ResponseService.success;
import static com.example.task.dto.constant.Pageable.defaultLimit;
import static com.example.task.dto.constant.Pageable.defaultPage;

@RestController
@RequestMapping(value = "/api/permission-group")
@AllArgsConstructor
public class PermissionGroupAPI {
    private IPermissionGroupService permissionGroupService;

    @PostMapping(value = "/create")
    public ResponseEntity<ResponseService<PermissionGroupDTO>> createPermissionGroup(@RequestBody PermissionGroupDTO permissionGroupDTO) {
        try {
            permissionGroupService.save(permissionGroupDTO);
            success();
        } catch (Exception ex) {
            ResponseService<PermissionGroupDTO> responseService = new ResponseService<>();
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    @PutMapping
    public ResponseEntity<ResponseService<PermissionGroupDTO>> updatePermissionGroup(@RequestBody PermissionGroupDTO permissionGroupDTO) {
        try {
            permissionGroupService.update(permissionGroupDTO);
            success();
        } catch (Exception ex) {
            ResponseService<PermissionGroupDTO> responseService = new ResponseService<>();
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    @DeleteMapping
    public ResponseEntity<ResponseService<PermissionGroupDTO>> deletePermissionGroup(@RequestBody PermissionGroupDTO permissionGroupDTO) {
        try {
            permissionGroupService.delete(permissionGroupDTO);
            success();
        } catch (Exception ex) {
            ResponseService<PermissionGroupDTO> responseService = new ResponseService<>();
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    @PostMapping(path = "/list")
    public ResponseEntity<ResponseService<List<PermissionGroupDTO>>> listPermissionGroup(
            @RequestParam(required = false, defaultValue = "" + defaultPage + "") Integer page,
            @RequestParam(required = false, defaultValue = "" + defaultLimit + "") Integer limit) {
        try {
            Pageable pageable = PageRequest.of(page - 1, limit);
            Page<PermissionGroupDTO> listPermissions = permissionGroupService.query(pageable);
            return new ResponseEntity<>(new ResponseService<>(listPermissions.getContent(), listPermissions.getTotalPages(), page), HttpStatus.OK);
        } catch (Exception ex) {
            ResponseService<List<PermissionGroupDTO>> responseService = new ResponseService<>();
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
    }
}
