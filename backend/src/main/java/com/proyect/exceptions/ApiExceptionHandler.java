package com.proyect.exceptions;

import com.proyect.dtos.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    public static final Logger log = LoggerFactory.getLogger(ApiExceptionHandler.class);

    public static final String BAD_REQUEST = "The request is malformed.";

    public static final String ACCESS_DENIED = "Access denied.";

    @ExceptionHandler({ApiException.class})
    @ResponseBody
    public ResponseEntity<ResponseDto> apiExceptionType(ApiException e) {
        ResponseDto error = new ResponseDto(e.getCode(), e.getMessage());
        switch (e.getCode()) {
            case 300 -> {
                log.error("Error:" + error.getDetail());
                return new ResponseEntity<>(error, HttpStatus.MULTIPLE_CHOICES);
            }
            case 401 -> {
                log.error("Error:" + error.getDetail());
                return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
            }
            case 404 -> {
                log.error("Error:" + error.getDetail());
                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
            }
            case 409 -> {
                log.error("Error:" + error.getDetail());
                return new ResponseEntity<>(error, HttpStatus.CONFLICT);
            }
            default -> {
                log.error("Error:" + error.getDetail(), e);
                return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    /**
     * Catch exceptions from malformed requests with NotNull del Dto.
     */

    @ExceptionHandler({MethodArgumentNotValidException.class, HttpRequestMethodNotSupportedException.class})
    @ResponseBody
    public ResponseEntity<ResponseDto> methodArgumentNotValidException() {
        ResponseDto error = new ResponseDto(400, BAD_REQUEST);
        log.error("Error:" + error.getDetail());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({AccessDeniedException.class})
    @ResponseBody
    public ResponseEntity<ResponseDto> methodAccessDeniedException() {
        ResponseDto error = new ResponseDto(401, ACCESS_DENIED);
        log.error("Error:" + error.getDetail());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({Throwable.class})
    @ResponseBody
    public ResponseEntity<?> fatalException(Exception e) {
        log.error("Error", e);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}