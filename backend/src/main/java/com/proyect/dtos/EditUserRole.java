package com.proyect.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class EditUserRole {

    @NotNull
    private Integer idUser;

    @NotNull
    private String rol;

}
