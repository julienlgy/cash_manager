package com.example.factory;

import com.example.exception.BadRequestException;
import com.example.model.request.CommandRequest;
import com.example.model.request.HelloRequest;
import com.example.model.request.PasswordRequest;
import com.example.model.request.Request;

public abstract class RequestFactory {
    public static Request create(String receivedBuffer) throws BadRequestException {
        if (receivedBuffer == null) throw new BadRequestException("Null request");
        String[] splittedBuffer = receivedBuffer.split(" ");
        Request.REQUEST type = Request.getRequestType(splittedBuffer[0]);
        if (type == Request.REQUEST.HELLO && splittedBuffer[1].equals("FROM")) {
            return new HelloRequest(splittedBuffer[2]);
        } else if (type == Request.REQUEST.COMMAND) {
            if (splittedBuffer[2] == "WITH")
                return new CommandRequest(splittedBuffer[1], splittedBuffer[3]);
            else
                return new CommandRequest(splittedBuffer[1], null);
        } else if (type == Request.REQUEST.PASSWORD) {
            return new PasswordRequest(splittedBuffer[1]);
        }
        throw new BadRequestException(receivedBuffer);
    }
}
