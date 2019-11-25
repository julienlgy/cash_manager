package com.example.session;

import com.example.exception.BadRequestException;
import com.example.exception.NullRequestException;
import com.example.factory.RequestFactory;
import com.example.factory.ResponseFactory;
import com.example.model.Client;
import com.example.model.request.CommandRequest;
import com.example.model.request.Request;
import com.example.model.response.ArticleResponse;
import com.example.model.response.BooleanResponse;
import com.example.model.response.DefaultResponse;
import com.example.model.response.Response;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ClientSocketHandler implements Runnable {
    private Client c;
    public ClientSocketHandler(Client c) {
        this.c = c;
    }

    public void listen() throws IOException {
        BufferedReader in =  new BufferedReader(new InputStreamReader(c.getSocket().getInputStream()));
        DataOutputStream out = new DataOutputStream(c.getSocket().getOutputStream());
        while (true) {
            try {
                Request req = RequestFactory.create(in.readLine());
                if (!req.needAction()) {
                    Response res = ResponseFactory.create(req.getDefaultResponse());
                    out.writeBytes(res.getResponse());
                } else {
                    if (req.getType() == Request.REQUEST.COMMAND) {
                        Response res = commandHandler(((CommandRequest) req));
                        out.writeBytes(res.getResponse());
                    }
                }
            } catch (BadRequestException e) {
                out.writeBytes(new DefaultResponse().getResponse());
            } catch (NullRequestException n) {
                /* TODO  globalize the close statement */
                c.getSocket().close();
                in.close();
                out.close();
                break;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    Response commandHandler(CommandRequest req) throws IOException, JSONException {
        BooleanResponse res = new BooleanResponse(false);
        switch(req.getCommand()) {
            case "ADD_ART":
                res.RESPONSE = c.getCart().add_art(req.getArgs());
                break;
            case "REM_ART":
                res.RESPONSE = c.getCart().rem_art(req.getArgs());
                break;
            case "GET_ART":
                return new ArticleResponse(c.getCart().get_art(req.getArgs()));
        }
        return res;
    }

    @Override
    public void run() {
        try {
            listen();
        } catch (IOException e) {
            System.out.println("error 5 " + e.getMessage());
        }
    }
}
