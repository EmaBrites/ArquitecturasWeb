package com.exa.accountservice.controller.handler;

import com.exa.accountservice.dto.ApiErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.security.auth.login.AccountNotFoundException;
import java.time.format.DateTimeParseException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiErrorDTO> handleIllegalStateException(IllegalStateException ex) {
        return ResponseEntity
                .badRequest()
                .body(ApiErrorDTO.builder().error("Illegal State").message(ex.getMessage()).build());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorDTO> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity
                .badRequest()
                .body(ApiErrorDTO.builder().error("Illegal Argument").message(ex.getMessage()).build());
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<ApiErrorDTO> handleDateTimeParseException(DateTimeParseException ex) {
        return ResponseEntity
                .badRequest()
                .body(ApiErrorDTO.builder().error("Date Time Parse Error").message("Invalid date format: " + ex.getParsedString()).build());
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ApiErrorDTO> handleAccountNotFoundException(AccountNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiErrorDTO.builder().error("Account Not Found").message(ex.getMessage()).build());
    }
}
