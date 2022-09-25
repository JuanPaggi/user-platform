package com.proyect.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class RoleModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role")
    private int idRole;

    @Column(unique = true)
    private String role;

    @ManyToMany(mappedBy = "roles")
    private Collection<UserModel> users;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "roles_privileges",
            joinColumns =
            @JoinColumn(name = "id_role", referencedColumnName = "id_role"),
            inverseJoinColumns =
            @JoinColumn(name = "id_privilege", referencedColumnName = "id_privilege")
    )
    private Collection<PrivilegeModel> privileges;

}
