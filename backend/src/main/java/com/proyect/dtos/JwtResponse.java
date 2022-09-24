package com.proyect.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serial;
import java.io.Serializable;

public record JwtResponse(@JsonProperty("token") String jwtToken) implements Serializable {

	@Serial
	private static final long serialVersionUID = -8091879091924046844L;

}