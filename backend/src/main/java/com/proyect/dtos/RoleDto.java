package com.proyect.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RoleDto {

    private String role;
    private List<String> privileges = new ArrayList<>();

}
