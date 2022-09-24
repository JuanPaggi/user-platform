package com.proyect.services;

import com.proyect.dtos.UserDto;
import com.proyect.exceptions.ApiException;
import com.proyect.models.UserModel;
import com.proyect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServices {

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder bcryptEncoder;

    private final UserRepository userRepository;

    public UserServices(@Autowired AuthenticationManager authenticationManager, @Autowired PasswordEncoder bcryptEncoder, @Autowired UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.bcryptEncoder = bcryptEncoder;
        this.userRepository = userRepository;
    }

    public void authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            throw new ApiException(401, "INVALID_CREDENTIALS");
        }
    }

    public UserModel save(UserDto user) {
        UserModel newUser = new UserModel();
        newUser.setUser(user.getUser());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        return userRepository.save(newUser);
    }

}
