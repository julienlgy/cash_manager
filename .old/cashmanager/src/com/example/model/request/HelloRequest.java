package com.example.model.request;

import com.example.model.response.Response;

public class HelloRequest implements Request {
    private REQUEST type;
    private String sessionId;

    public HelloRequest(String sessionId) {
        this.type = REQUEST.HELLO;
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    @Override
    public REQUEST getType() {
        return type;
    }

    @Override
    public Response.RESPONSE getDefaultResponse() {
        return Response.RESPONSE.REQ_PASSWD;
    }

    @Override
    public boolean needAction() {
        return false;
    }
}
