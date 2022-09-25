package com.proyect.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class UserDto {

    private Integer idUser;

    @NotNull
    private String email;

    @NotNull
    private String name;

    @NotNull
    private String lastName;

    @NotNull
    private String user;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private Date createDate;

    private Date updateDate;

    private boolean enable;

    private boolean locked;

    private boolean mailVerify;

    private List<String> privileges = new ArrayList<>();

}