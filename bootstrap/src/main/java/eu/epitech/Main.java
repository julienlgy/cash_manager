package eu.epitech;

import eu.epitech.controller.Database;
import eu.epitech.controller.JsonParser;
import eu.epitech.model.Account;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        JsonParser jsonparse = new JsonParser();
        if (args.length > 0) {
            File file = new File(args[0]);
            Account account = jsonparse.parseAccount(file);
            if (account != null) {
                account.show();
            }
        } else {
            Database db = new Database();
            String name;
            Scanner sc = new Scanner(System.in);
            System.out.print("What is your name ? : ");
            name = sc.nextLine();
            /*System.out.print("Accounts number ? (,) : ");
            accounts = sc.nextLine();
            System.out.println("Showing accounts "+accounts+ " for user " + name);*/
            Integer userId = -1;
            if ((userId = db.isValidUser(name)) >= 0) {
                List<Account> userAccounts = db.getAccounts(userId);
                if (userAccounts.size() == 0) {
                    System.out.println("This user hasn't account yet.");
                } else {
                    Iterator<Account> it = userAccounts.iterator();
                    while (it.hasNext()) {
                        Account ac = it.next();
                        System.out.println("The user "+name+" has the account "+ac.getId() + " with "+ac.getCash()+ "$ inside.");
                    }
                }
            } else {
                System.out.println("User not found.");
            }
        }
    }
}
