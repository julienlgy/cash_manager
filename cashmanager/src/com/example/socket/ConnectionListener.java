package com.example.socket;

import com.example.exception.BadRequestException;
import com.example.model.response.DefaultResponse;

import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Simple connection listener in TCP.
 * jlegay 2019
 * #Epitech
 */
public class ConnectionListener {
    public ConnectionListener(int port, String serverpass) throws Exception {
        SocketController socket = SocketController.getInstance();
        ServerSocket welcomeSocket = new ServerSocket(port);
        while (true) {
            System.out.println(socket.size());
            Socket connectionSocket = welcomeSocket.accept();
            try {
                AuthSystem auth = new AuthSystem(serverpass, connectionSocket);
                auth.socketController = socket;
                Thread thread = new Thread(auth);
                thread.start();
            } catch (Exception e) {

            }
        }
    }
}