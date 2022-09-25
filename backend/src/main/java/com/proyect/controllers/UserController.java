package com.proyect.controllers;

import com.proyect.dtos.*;
import com.proyect.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserServices userServices;

    public UserController(@Autowired UserServices userServices) {
        this.userServices = userServices;
    }

    @GetMapping()
    public ResponseEntity<UserDto> getUser() {
        return ResponseEntity.ok(userServices.getUser());
    }

    @GetMapping("/list")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    public ResponseEntity<List<UserDto>> getUsers() {
        return ResponseEntity.ok(userServices.getUsers());
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> saveUser(@Validated @RequestBody UserDto user) {
        userServices.save(user);
        return ResponseEntity.ok(ResponseDto.getInstanceOk());
    }

    @PutMapping("/changePassword/withLogin")
    public ResponseEntity<ResponseDto> changePasswordWithLogin(@Validated @RequestBody ChangePasswordDto body) {
        userServices.changePasswordWithLogin(body.getLastPassword(), body.getNewPassword());
        return ResponseEntity.ok(ResponseDto.getInstanceOk());
    }

    @PutMapping("/editDataUser")
    public ResponseEntity<ResponseDto> editDataUser(@Validated @RequestBody UserDto body) {
        userServices.editDataUser(body);
        return ResponseEntity.ok(ResponseDto.getInstanceOk());
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN')")
    @PutMapping("/editUserRole")
    public ResponseEntity<ResponseDto> editUserRole(@Validated @RequestBody EditUserRole body) {
        userServices.editRole(body.getIdUser(), body.getRol());
        return ResponseEntity.ok(ResponseDto.getInstanceOk());
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN')")
    @GetMapping("/roles")
    public ResponseEntity<List<RoleDto>> getRoles() {
        return ResponseEntity.ok(userServices.getRoles());
    }

}
