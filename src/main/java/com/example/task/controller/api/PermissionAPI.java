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

import static com.example.task.dto.constant.Pageable.defaultLimit;
import static com.example.task.dto.constant.Pageable.defaultPage;

@RestController
@RequestMapping(value = "/api/permission")
@AllArgsConstructor
public class PermissionAPI extends CommonAPI {
    private PermissionService permissionService;

    @GetMapping(value = "/list")
    public ResponseEntity<ResponseService> listPermission(@RequestParam(required = false, defaultValue = "" + defaultPage + "") Integer page,
                                                          @RequestParam(required = false, defaultValue = "" + defaultLimit + "") Integer limit) {

        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<PermissionDTO> pageResult = permissionService.query(pageable);
        return new ResponseEntity<>(new ResponseService("success", pageResult.getContent(), pageResult.getTotalPages(), page,  limit,"200"), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseService> createPermission(@RequestBody PermissionDTO permissionDTO) {
        responseService.setMessage("Insert success");
        try {
            permissionService.save(permissionDTO);
        } catch (Exception ex) {
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(responseService);
    }


    @PutMapping
    public ResponseEntity<ResponseService> updatePermission(@RequestBody PermissionDTO permissionDTO) {
        responseService.setMessage("Update success");
        try {
            permissionService.update(permissionDTO);
        } catch (Exception ex) {
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(responseService);
    }

    @DeleteMapping
    public ResponseEntity<ResponseService> deletePermission(@RequestBody PermissionDTO permissionDTO) {
        responseService.setMessage("Delete Success");
        try {
            permissionService.delete(permissionDTO.getIds());
        } catch (Exception ex) {
            responseService.setMessage(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(responseService);
    }
}
