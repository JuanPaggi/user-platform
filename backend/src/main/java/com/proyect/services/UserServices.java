package com.proyect.services;

import com.proyect.dtos.RoleDto;
import com.proyect.dtos.UserDto;
import com.proyect.exceptions.ApiException;
import com.proyect.models.RoleModel;
import com.proyect.models.UserModel;
import com.proyect.repository.RoleRepository;
import com.proyect.repository.UserRepository;
import com.proyect.utils.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServices {

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder bcryptEncoder;

    private final UserRepository userRepository;

    private final RoleRepository rolRepository;

    public UserServices(@Autowired AuthenticationManager authenticationManager, @Autowired PasswordEncoder bcryptEncoder, @Autowired UserRepository userRepository, @Autowired RoleRepository rolRepository) {
        this.authenticationManager = authenticationManager;
        this.bcryptEncoder = bcryptEncoder;
        this.userRepository = userRepository;
        this.rolRepository = rolRepository;
    }

    public void authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            throw new ApiException(401, "INVALID_CREDENTIALS");
        }
    }

    public void save(UserDto user) {
        UserModel newUser = new UserModel();
        newUser.setEmail(user.getEmail());
        newUser.setName(user.getName());
        newUser.setLastName(user.getLastName());
        newUser.setUser(user.getUser());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        newUser.setRoles(Collections.singletonList(rolRepository.findByRole(Roles.ROLE_USER.name())));
        try {
            userRepository.save(newUser);
        } catch (DataIntegrityViolationException e) {
            throw new ApiException(409, e.getMessage());
        }
    }

    public UserDto getUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserModel userDB = userRepository.findByUser(userDetails.getUsername());
        return extractUser(userDB);
    }

    public List<UserDto> getUsers() {
        List<UserModel> users = userRepository.findAll();
        List<UserDto> exit = new ArrayList<>();
        users.forEach(it -> {
            UserDto user = extractUser(it);
            exit.add(user);
        });
        return exit;
    }

    private UserDto extractUser(UserModel it) {
        UserDto user = new UserDto();
        user.setIdUser(it.getIdUser());
        user.setUser(it.getUser());
        user.setName(it.getName());
        user.setLastName(it.getLastName());
        user.setEmail(it.getEmail());
        it.getRoles().forEach(
                it2 -> it2.getPrivileges().forEach(
                        it3 -> user.getPrivileges().add(it3.getPrivilege())
                )
        );
        return user;
    }

    public void editRole(Integer idUserEdit, String newRol) {
        Optional<UserModel> userDB = userRepository.findById(idUserEdit);
        if (userDB.isEmpty()) {
            throw new ApiException(404, "User not found.");
        }
        RoleModel roleDB = rolRepository.findByRole(newRol);
        if (roleDB == null || roleDB.getRole().equals(Roles.ROLE_SUPER_ADMIN.name())) {
            throw new ApiException(404, "Role not found.");
        }
        userDB.get().getRoles().clear();
        userDB.get().getRoles().add(roleDB);
        userRepository.save(userDB.get());
    }

    public List<RoleDto> getRoles() {
        List<RoleModel> roles = rolRepository.findAll();
        List<RoleDto> exit = new ArrayList<>();
        roles.forEach(it -> {
            RoleDto role = new RoleDto();
            it.getPrivileges().forEach(it2 -> role.getPrivileges().add(it2.getPrivilege()));
            role.setRole(it.getRole());
            exit.add(role);
        });
        return exit;
    }

}
