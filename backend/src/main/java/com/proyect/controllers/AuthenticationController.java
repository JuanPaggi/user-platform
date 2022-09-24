package com.proyect.controllers;

import com.proyect.configuration.JwtTokenUtil;
import com.proyect.dtos.JwtRequest;
import com.proyect.dtos.JwtResponse;
import com.proyect.services.JwtService;
import com.proyect.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/authenticate")
public class AuthenticationController {

    private final JwtTokenUtil jwtTokenUtil;

    private final JwtService jwtService;

    private final UserServices userServices;

    public AuthenticationController(@Autowired JwtTokenUtil jwtTokenUtil, @Autowired JwtService jwtService, @Autowired UserServices userServices) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.jwtService = jwtService;
        this.userServices = userServices;
    }

    @PostMapping()
    public ResponseEntity<?> createAuthenticationToken(@Validated @RequestBody JwtRequest authenticationRequest) {
        userServices.authenticate(authenticationRequest.getUser(), authenticationRequest.getPassword());
        final UserDetails userDetails = jwtService.loadUserByUsername(authenticationRequest.getUser());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

}