package com.example.Journal.exception;

public class JournalAlreadyExistsException extends RuntimeException {
    public JournalAlreadyExistsException(String message) {
        super(message);
    }
}