package eu.epitech.controller;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.epitech.model.Account;
import eu.epitech.model.MapperAccount;
import eu.epitech.model.MapperUser;
import eu.epitech.model.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class JsonParser {
    ObjectMapper mapper;
    public JsonParser() {
        mapper = new ObjectMapper();
    }

    public Account parseAccount(File jsonString) {
        try {
            Account account =  mapper.readValue(jsonString, Account.class);
            return account;
        } catch (JsonParseException ej) {
            System.out.println("An error occured when parsin JSON. Please check him...");
            return null;
        } catch (IOException e) {
            System.out.println("An unknown error occured.");
            System.out.println(e.getMessage());
            return null;
        }
    }

    public ArrayList<Account> parseAccounts(File jsonString) {
        try {
            ArrayList<Account> accounts = new ArrayList<Account>();
            MapperAccount mpa = mapper.readValue(jsonString, MapperAccount.class);
            Iterator<Account> it = mpa.getList().iterator();
            while(it.hasNext())
                accounts.add(it.next());
            return accounts;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ArrayList<Account>();
    }

    public ArrayList<User> parseUsers(File jsonString) {
        try {
            ArrayList<User> users = new ArrayList<User>();
            MapperUser mpa = mapper.readValue(jsonString, MapperUser.class);
            Iterator<User> it = mpa.getList().iterator();
            while(it.hasNext())
                users.add(it.next());
            return users;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ArrayList<User>();
    }
}

