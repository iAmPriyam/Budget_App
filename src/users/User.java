package users;

import accounts.Account;

import java.util.LinkedList;

public class User {
    private String name;
    private LinkedList<Account> accounts;
    private double monthlyBudget;

    public User(String name){
        this.name = name;
        accounts = new LinkedList<>();
        monthlyBudget = 0;
    }

    public double getMonthlyBudget() {
        return monthlyBudget;
    }

    public void setMonthlyBudget(double money) {
        this.monthlyBudget = money;
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
}
