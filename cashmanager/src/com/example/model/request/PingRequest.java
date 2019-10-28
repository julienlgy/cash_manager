package com.example.model.request;

import com.example.model.response.Response;

public class PingRequest implements Request {
    @Override
    public REQUEST getType() {
        return REQUEST.PING;
    }

    @Override
    public Response.RESPONSE getDefaultResponse() {
        return Response.RESPONSE.PONG;
    }

    @Override
    public boolean needAction() {
        return false;
    }
}
