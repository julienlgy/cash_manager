package eu.epitech.controller;

import eu.epitech.model.Account;
import eu.epitech.model.User;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Database {
    ArrayList<Account> accounts;
    ArrayList<User> users;

    public Database() {
        System.out.println("Populating database...");
        this.accounts = Populate.populateAccounts();
        this.users = Populate.populateUsers();
        System.out.println(" | Populated.");
    }

    public Integer isValidUser(String name) {
        Iterator<User> it =  this.users.iterator();
        while(it.hasNext()) {
            User user = it.next();
            if (user.getName().toLowerCase().equals(name.toLowerCase())) {
                return user.getId();
            }
        }
        return -1;
    }

    public List<Account> getAccounts(Integer userId) {
        List<Account> accounts = new ArrayList<Account>();
        Iterator<Account> it = this.accounts.iterator();
        while (it.hasNext()) {
            Account account = it.next();
            if (account.getAccountOwner() == userId) accounts.add(account);
        }
        return accounts;
    }
}
