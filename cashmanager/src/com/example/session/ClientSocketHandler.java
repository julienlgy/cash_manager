package com.example.session;

import com.example.exception.BadRequestException;
import com.example.exception.NullRequestException;
import com.example.factory.RequestFactory;
import com.example.factory.ResponseFactory;
import com.example.model.Client;
import com.example.model.request.Request;
import com.example.model.response.DefaultResponse;
import com.example.model.response.Response;

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
                }
            } catch (BadRequestException e) {
                out.writeBytes(new DefaultResponse().getResponse());
            } catch (NullRequestException n) {
                // need to globalize the close
                c.getSocket().close();
                in.close();
                out.close();
                break;
            }
        }
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
