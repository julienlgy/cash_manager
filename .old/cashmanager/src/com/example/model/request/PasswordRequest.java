package com.example.model.request;

import com.example.model.response.Response;

public class PasswordRequest implements Request {
    private String password;

    public PasswordRequest(String password) {
        this.password = password;
    }

    @Override
    public Response.RESPONSE getDefaultResponse() {
        return Response.RESPONSE.BOOLEAN;
    }

    @Override
    public REQUEST getType() {
        return REQUEST.PASSWORD;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean needAction() {
        return false;
    }
}
