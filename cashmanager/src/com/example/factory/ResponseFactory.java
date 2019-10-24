package com.example.factory;

import com.example.model.response.BooleanResponse;
import com.example.model.response.ReqPasswdResponse;
import com.example.model.response.Response;

public class ResponseFactory {
    public static Response create(Response.RESPONSE type, String val) {
        if (type == Response.RESPONSE.REQ_PASSWD)
            return new ReqPasswdResponse();
        else if (type == Response.RESPONSE.BOOLEAN) {
            if (val instanceof String) {
                return new BooleanResponse(val);
            } else {
                return new BooleanResponse();
            }
        }
        return new BooleanResponse(false);
    }
}
