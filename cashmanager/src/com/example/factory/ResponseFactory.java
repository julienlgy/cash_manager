package com.example.factory;

import com.example.model.response.*;

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
        } else if (type == Response.RESPONSE.PONG) {
            return new PongResponse();
        } else if (type == Response.RESPONSE.ARTICLE) {
            return new ArticleResponse();
        }
        return new BooleanResponse(false);
    }

    public static Response create(Response.RESPONSE ress) {
        return create(ress, null);
    }
}
