package com.example.model;

import com.example.session.CartSyncHandler;
import com.example.session.ClientSocketHandler;

import java.net.Socket;

public class Client {
    private String sessionId;
    private Socket socket;
    private Thread thread;
    private ClientSocketHandler clientSocketHandler;
    private CartSyncHandler cartSyncHandler;
    public Client(String sessionId, Socket socket) {
        this.sessionId = sessionId;
        this.socket = socket;
        this.clientSocketHandler = new ClientSocketHandler(this);
        this.cartSyncHandler = new CartSyncHandler();
        this.thread = new Thread(clientSocketHandler);
    }

    public String getSessionId() {
        return sessionId;
    }

    public Socket getSocket() {
        return socket;
    }
    public CartSyncHandler getCart() {
        return cartSyncHandler;
    }
    public void listen() {
        thread.start();
    }
    public void unlisten() {
        thread.stop();
    }
}
