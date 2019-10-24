package com.example.socket;

import com.example.exception.BadRequestException;
import com.example.exception.NullRequestException;
import com.example.factory.RequestFactory;
import com.example.factory.ResponseFactory;
import com.example.model.Client;
import com.example.model.request.HelloRequest;
import com.example.model.request.PasswordRequest;
import com.example.model.request.Request;
import com.example.model.response.BooleanResponse;
import com.example.model.response.DefaultResponse;
import com.example.model.response.Response;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Auth system class : manage authing
 * jlegay 2019
 * Epitech
 */
public class AuthSystem implements Runnable {
    private String serverPassword;
    private Socket user;
    public SocketController socketController;
    public AuthSystem(String serverPasswd, Socket user) {
        this.serverPassword = serverPasswd;
        this.user = user;
    }

    public void connect(Socket user) throws Exception {
        BufferedReader inFromClient =
                new BufferedReader(new InputStreamReader(user.getInputStream()));
        DataOutputStream outToClient = new DataOutputStream(user.getOutputStream());
        Request req = RequestFactory.create(inFromClient.readLine());
        if (req.getType() == Request.REQUEST.HELLO) {
            Response res = ResponseFactory.create(req.getDefaultResponse(), null);
            outToClient.writeBytes(res.getResponse());
            waitForAuth(user, inFromClient, outToClient, ((HelloRequest)req).getSessionId(), 0);
        } else {
            throw new BadRequestException("no hello");
        }
    }

    public void waitForAuth(Socket user, BufferedReader in, DataOutputStream out, String sesssionId, int attemps)  throws Exception {
        Request req = RequestFactory.create(in.readLine());
        if (req.getType() == Request.REQUEST.PASSWORD) {
            BooleanResponse res = (BooleanResponse) ResponseFactory.create(Response.RESPONSE.BOOLEAN, null);
            if (this.serverPassword.equals(((PasswordRequest) req).getPassword())) {
                res.RESPONSE = true;
                if (this.socketController.add(new Client(sesssionId, user)))
                    out.writeBytes(res.getResponse());
                else {
                    res.RESPONSE = false;
                    out.writeBytes("User already logged\n");
                    user.close();
                    out.close();
                    in.close();
                }
            } else {
                res.RESPONSE = false;
                out.writeBytes(res.getResponse());
                if (attemps == 3) {
                    out.writeBytes("AUTH FAILED\n");
                    out.close();
                    in.close();
                    user.close();
                } else {
                    waitForAuth(user, in, out, sesssionId, attemps + 1);
                }
            }
        } else {
            throw new BadRequestException("No password");
        }
    }

    @Override
    public void run() {
        try {
            this.connect(user);
        } catch (BadRequestException e) {
            try {
                DataOutputStream outToClient = new DataOutputStream(user.getOutputStream());
                DefaultResponse defaultResponse = new DefaultResponse();
                try {
                    outToClient.writeBytes(defaultResponse.getResponse());
                    user.close();
                    outToClient.close();
                } catch (Exception y) {
                    user.close();
                    outToClient.close();
                }
            } catch (Exception u) {
                System.out.println(u.getMessage());
            }
        } catch(NullRequestException n) {
            try {
                user.close();
            } catch(Exception i) {

            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}