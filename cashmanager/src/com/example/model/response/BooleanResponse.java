package com.example.model.response;

public class BooleanResponse implements Response {
    public Boolean RESPONSE;
    public BooleanResponse(Boolean response) {
        this.RESPONSE = response;
    }
    public BooleanResponse(String response) {
        this.RESPONSE = (response.toLowerCase() == "ok");
    }
    public BooleanResponse() {
        this.RESPONSE = false;
    }

    @Override
    public String getResponse() {
        return (RESPONSE) ? "OK\n" : "KO\n";
    }
}
