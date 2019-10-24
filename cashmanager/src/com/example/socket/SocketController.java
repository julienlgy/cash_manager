package com.example.socket;

import com.example.model.Client;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Singleton class for managing connected Clients.
 * jlegay 2019
 * #Epitech
 */
public class SocketController {
    // singeleton
    static private SocketController instance;
    static SocketController getInstance() {
        if (instance == null) return new SocketController();
        return instance;
    }

    private ArrayList arrayOfClient;
    SocketController() {
        this.arrayOfClient = new ArrayList<Client>();
    }
    boolean add(Client client) {
        if (get(client.getSessionId()) == null) {
            arrayOfClient.add(client);
            return true;
        }
        return false;
    }
    boolean remove(Client client) {
        return arrayOfClient.remove(client);
    }
    Client get(String sessionId) {
        Iterator<Client> it = arrayOfClient.iterator();
        while(it.hasNext()) {
            Client c = it.next();
            if (c.getSessionId().equals(sessionId)) return c;
        }
        return null;
    }
    int size() {
        return arrayOfClient.size();
    }
    ArrayList<Client> getAll() { return arrayOfClient; }
}
