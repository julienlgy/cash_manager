package eu.epitech.controller;

import eu.epitech.model.Account;
import eu.epitech.model.User;

import java.io.File;
import java.util.ArrayList;

public class Populate {
    public static ArrayList<Account> populateAccounts() {
        JsonParser jsonParser = new JsonParser();
        return jsonParser.parseAccounts(new File("tests/populate_accounts.json"));
    }
    public static ArrayList<User> populateUsers() {
        JsonParser jsonParser = new JsonParser();
        return jsonParser.parseUsers(new File("tests/populate_users.json"));
    }
}
