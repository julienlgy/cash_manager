package com.example.model;

import com.example.session.ClientSocketHandler;

import java.net.Socket;

public class Client {
    private String sessionId;
    private Socket socket;
    private Thread thread;
    private ClientSocketHandler clientSocketHandler;
    public Client(String sessionId, Socket socket) {
        this.sessionId = sessionId;
        this.socket = socket;
        this.clientSocketHandler = new ClientSocketHandler(this);
        this.thread = new Thread(clientSocketHandler);
    }

    public String getSessionId() {
        return sessionId;
    }

    public Socket getSocket() {
        return socket;
    }

    public void listen() {
        thread.start();
    }
    public void unlisten() {
        thread.stop();
    }
}
