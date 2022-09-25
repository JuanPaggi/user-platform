package com.proyect.services;

import com.proyect.exceptions.ApiException;
import com.proyect.models.PrivilegeModel;
import com.proyect.models.RoleModel;
import com.proyect.repository.UserRepository;
import com.proyect.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class JwtService implements UserDetailsService {

    private final UserRepository userRepository;

    public JwtService(@Autowired UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String user) {
        UserModel userModel = userRepository.findByUser(user);
        if (userModel == null) {
            throw new ApiException(401, "User not found with username: " + user);
        }
        return new User(userModel.getUser(), userModel.getPassword(), true, true, true, true,
                getAuthorities(userModel.getRoles()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<RoleModel> roles) {
        return getGrantedAuthorities(getPrivileges(roles));
    }

    private List<String> getPrivileges(Collection<RoleModel> roles) {
        List<String> privileges = new ArrayList<>();
        List<PrivilegeModel> collection = new ArrayList<>();
        for (RoleModel role : roles) {
            privileges.add(role.getRole());
            collection.addAll(role.getPrivileges());
        }
        for (PrivilegeModel item : collection) {
            privileges.add(item.getPrivilege());
        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }


}