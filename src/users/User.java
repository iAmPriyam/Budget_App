package users;

import accounts.Account;

import java.util.LinkedList;

public class User {
    private String name;
    private LinkedList<Account> accounts;
    private String password;

    public User(String name, String password){
        this.name = name;
        this.password = password;
        accounts = new LinkedList<>();
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }
    public void deleteAccount(Account account) {
        accounts.remove(account);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LinkedList<Account> getAccounts() {
        return accounts;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
