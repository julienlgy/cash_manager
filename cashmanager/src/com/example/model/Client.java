package com.example.model;

import java.net.Socket;

public class Client {
    private String sessionId;
    private Socket socket;
    public Client(String sessionId, Socket socket) {
        this.sessionId = sessionId;
        this.socket = socket;
    }

    public String getSessionId() {
        return sessionId;
    }

    public Socket getSocket() {
        return socket;
    }
}
