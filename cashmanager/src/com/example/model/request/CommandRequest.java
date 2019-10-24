package com.example.model.request;

import com.example.model.response.Response;

public class CommandRequest implements Request {
    private REQUEST type;
    private String command;
    private String args;

    public CommandRequest(String command, String args) {
        this.type = REQUEST.COMMAND;
        this.command = command;
        this.args = args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    public String getArgs() {
        return args;
    }

    @Override
    public REQUEST getType() {
        return type;
    }

    @Override
    public Response.RESPONSE getDefaultResponse() {
        return Response.RESPONSE.BOOLEAN;
    }

    @Override
    public boolean needAction() {
        return true;
    }
}
