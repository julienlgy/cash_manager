package com.example.exception;

public class NullRequestException extends Exception {
    @Override
    public String getMessage() {
        return "user disconnected";
    }
}
