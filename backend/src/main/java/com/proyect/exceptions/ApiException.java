package com.proyect.exceptions;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {
    protected final int code;

    public ApiException(int code, String message){
        super(message);
        this.code = code;
    }

}