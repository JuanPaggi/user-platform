package com.proyect.controllers;

import com.proyect.dtos.ResponseDto;
import com.proyect.dtos.UserDto;
import com.proyect.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserServices userServices;

    public UserController(@Autowired UserServices userServices) {
        this.userServices = userServices;
    }

    @GetMapping("/hello")
    public ResponseEntity<String> firstPage() {
        return ResponseEntity.ok("Hello World");
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> saveUser(@Validated @RequestBody UserDto user) {
        userServices.save(user);
        return ResponseEntity.ok(ResponseDto.getInstanceOk());
    }

}
