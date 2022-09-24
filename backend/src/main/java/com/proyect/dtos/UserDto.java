package com.proyect.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserDto {

    @NotNull
    private String user;

    @NotNull
    private String password;

}