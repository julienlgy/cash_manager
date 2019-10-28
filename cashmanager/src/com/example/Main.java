package com.example;

import com.example.socket.*;

public class Main {

    public static void main(String[] args) {
	    System.out.println("Starting cashmanager server");
	    try {
            new ConnectionListener(8000, "test");
        } catch (Exception e) {
	        System.out.println(e.getMessage());
	        System.exit(1);
        }
    }
}
