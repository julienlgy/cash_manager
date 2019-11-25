package com.example.model.response;

public class DefaultResponse implements Response {
    @Override
    public String getResponse() {
        return "Bad Request\n";
    }
}
