package com.example.exception;

public class BadRequestException extends Exception {
    private String baseRequest;

    public BadRequestException(String baseRequest) {
        this.baseRequest = baseRequest;
    }

    @Override
    public String toString() {
        return "Internal server error : Cannot parse the request "+this.baseRequest;
    }

    @Override
    public String getMessage() {
        return toString();
    }
}
