package com.proyect.utils;

import com.proyect.models.PrivilegeModel;
import com.proyect.models.RoleModel;
import com.proyect.repository.PrivilegeRepository;
import com.proyect.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
public class SetupDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    private final RoleRepository roleRepository;

    private final PrivilegeRepository privilegeRepository;

    public SetupDataLoader(@Autowired RoleRepository roleRepository, @Autowired PrivilegeRepository privilegeRepository) {
        this.roleRepository = roleRepository;
        this.privilegeRepository = privilegeRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) return;
        PrivilegeModel readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
        PrivilegeModel writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
        PrivilegeModel fullPrivilege = createPrivilegeIfNotFound("FULL_ACCESS");
        createRoleIfNotFound(Roles.ROLE_SUPER_ADMIN.name(), Arrays.asList(writePrivilege, readPrivilege, fullPrivilege));
        createRoleIfNotFound(Roles.ROLE_ADMIN.name(), Arrays.asList(writePrivilege, readPrivilege));
        createRoleIfNotFound(Roles.ROLE_USER.name(), Collections.singletonList(readPrivilege));
        alreadySetup = true;
    }

    @Transactional
    PrivilegeModel createPrivilegeIfNotFound(String name) {
        PrivilegeModel privilege = privilegeRepository.findByPrivilege(name);
        if (privilege == null) {
            privilege = new PrivilegeModel();
            privilege.setPrivilege(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    void createRoleIfNotFound(String name, Collection<PrivilegeModel> privileges) {
        RoleModel role = roleRepository.findByRole(name);
        if (role == null) {
            role = new RoleModel();
            role.setRole(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
    }

}
