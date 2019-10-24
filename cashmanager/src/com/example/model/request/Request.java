package com.example.model.request;

import com.example.exception.BadRequestException;
import com.example.model.response.Response;

/**
 * jlegay 2019
 *  Request parser server side {JAVA}
 */
public interface Request {
    // STATIC OPTIONS
    enum REQUEST {
        COMMAND,
        HELLO,
        PASSWORD,
        PAYMENT
    }
    static REQUEST getRequestType(String type) throws BadRequestException {
        switch(type) {
            case "COMMAND" : return REQUEST.COMMAND;
            case "HELLO" : return REQUEST.HELLO;
            case "PASSWORD" : return REQUEST.PASSWORD;
            case "PAYMENT" : return REQUEST.PAYMENT;
            default : throw new BadRequestException(type);
        }
    }

    REQUEST getType();

    Response.RESPONSE getDefaultResponse();
}