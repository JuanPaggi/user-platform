package com.proyect.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private int idUser;

    @Column(unique = true)
    private String email;

    @Column
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(unique = true)
    private String user;

    @Column
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns =
            @JoinColumn(name = "id_user", referencedColumnName = "id_user"),
            inverseJoinColumns =
            @JoinColumn(name = "id_role", referencedColumnName = "id_role")
    )
    private List<RoleModel> roles;

}