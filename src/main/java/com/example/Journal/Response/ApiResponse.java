package com.example.Journal.Response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;
    private String timestamp;

    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public String getTimestamp() {
        return timestamp;
    }
}