package com.proyect.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "privileges")
@Getter
@Setter
public class PrivilegeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_privilege")
    private int idPrivilege;

    @Column(unique = true)
    private String privilege;

    @ManyToMany(mappedBy = "privileges")
    private Collection<RoleModel> roles;
}