package com.example.factory;

import com.example.exception.BadRequestException;
import com.example.exception.NullRequestException;
import com.example.model.request.*;

public abstract class RequestFactory {
    public static Request create(String receivedBuffer) throws BadRequestException, NullRequestException {
        if (receivedBuffer == null) throw new NullRequestException();
        String[] splittedBuffer = receivedBuffer.split(" ");
        Request.REQUEST type = Request.getRequestType(splittedBuffer[0]);
        if (type == Request.REQUEST.HELLO && splittedBuffer[1].equals("FROM")) {
            return new HelloRequest(splittedBuffer[2]);
        } else if (type == Request.REQUEST.COMMAND) {
            if (splittedBuffer[2].equals("WITH"))
                return new CommandRequest(splittedBuffer[1], splittedBuffer[3]);
            else
                return new CommandRequest(splittedBuffer[1], null);
        } else if (type == Request.REQUEST.PASSWORD) {
            return new PasswordRequest(splittedBuffer[1]);
        } else if (type == Request.REQUEST.PING) {
            return new PingRequest();
        }
        throw new BadRequestException(receivedBuffer);
    }
}
