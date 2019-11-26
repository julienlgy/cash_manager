package com.cashmanager.cashmanager.model;

public class Authentification {
    private String token;
    private boolean status;
    private String message;

    public Authentification(String token, boolean status) {
        this.token = token;
        this.status = status;

        if (this.status) { this.message = "OK"; }
        else { this.message = "KO"; }
    }

    public String getToken() { return this.token; }

    public Boolean getStatus() { return this.status; }

    public String getMessage() { return message; }
}
