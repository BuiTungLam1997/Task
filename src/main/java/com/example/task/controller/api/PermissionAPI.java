package com.example.task.controller.api;

import com.example.task.dto.PermissionDTO;
import com.example.task.dto.ResponseService;
import com.example.task.service.impl.PermissionService;
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
@RequestMapping(value = "/api/permission")
@AllArgsConstructor
public class PermissionAPI {
    private PermissionService permissionService;

    @GetMapping(value = "/list")
    public ResponseEntity<ResponseService<List<PermissionDTO>>> listPermission(
            @RequestParam(required = false, defaultValue = "" + defaultPage + "") Integer page,
            @RequestParam(required = false, defaultValue = "" + defaultLimit + "") Integer limit) {

        try {
            Pageable pageable = PageRequest.of(page - 1, limit);
            Page<PermissionDTO> pageResult = permissionService.query(pageable);
            return new ResponseEntity<>(new ResponseService<>("success", pageResult.getContent(), pageResult.getTotalPages(), page, limit, "200"), HttpStatus.OK);
        } catch (Exception ex) {
            ResponseService<List<PermissionDTO>> responseService = new ResponseService<>();
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<ResponseService<PermissionDTO>> createPermission(@RequestBody PermissionDTO permissionDTO) {
        try {
            permissionService.save(permissionDTO);
            success();
        } catch (Exception ex) {
            ResponseService<PermissionDTO> responseService = new ResponseService<>();
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
        return null;
    }


    @PutMapping
    public ResponseEntity<ResponseService<PermissionDTO>> updatePermission(@RequestBody PermissionDTO permissionDTO) {
        try {
            permissionService.update(permissionDTO);
            success();
        } catch (Exception ex) {
            ResponseService<PermissionDTO> responseService = new ResponseService<>();
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    @DeleteMapping
    public ResponseEntity<ResponseService<PermissionDTO>> deletePermission(@RequestBody PermissionDTO permissionDTO) {

        try {
            permissionService.delete(permissionDTO.getIds());
            success();
        } catch (Exception ex) {
            ResponseService<PermissionDTO> responseService = new ResponseService<>();
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
        return null;
    }
}
