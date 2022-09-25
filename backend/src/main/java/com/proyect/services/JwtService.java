package com.proyect.services;

import com.proyect.models.PrivilegeModel;
import com.proyect.models.RoleModel;
import com.proyect.repository.RoleRepository;
import com.proyect.repository.UserRepository;
import com.proyect.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class JwtService implements UserDetailsService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    public JwtService(@Autowired UserRepository userRepository, @Autowired RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String user) throws UsernameNotFoundException {
        UserModel userModel = userRepository.findByUser(user);
        if (userModel == null) {
            throw new UsernameNotFoundException("User not found with username: " + user);
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