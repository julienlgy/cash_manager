package com.example.model.response;

public interface Response {
    enum RESPONSE {
        BOOLEAN,
        REQ_PASSWD
    }
    static RESPONSE toResponse(String response) {
        switch(response) {
            case "BOOLEAN" : return RESPONSE.BOOLEAN;
            case "REQ_PASSWD" : return RESPONSE.REQ_PASSWD;
            default : return RESPONSE.BOOLEAN;
        }
    }

    String getResponse();
}
