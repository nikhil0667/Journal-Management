package com.example.Journal.exception;
import com.example.Journal.Response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(JournalNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFound(
            JournalNotFoundException ex) {

        return new ResponseEntity<>(
                new ApiResponse<>(false, ex.getMessage(), null),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(JournalAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Void>> handleAlreadyExists(
            JournalAlreadyExistsException ex) {

        return new ResponseEntity<>(
                new ApiResponse<>(false, ex.getMessage(), null),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationErrors(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors()
                .forEach(error ->
                        errors.put(error.getField(), error.getDefaultMessage()));

        return new ResponseEntity<>(
                new ApiResponse<>(false, "Validation failed", errors),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneric(Exception ex) {

        return new ResponseEntity<>(
                new ApiResponse<>(false, "Internal server error", null),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
