package users;

import accounts.Account;
import java.util.ArrayList;

public class User {
    private int id;
    private String name;
    private ArrayList<Account> accounts;
    private double monthlyBudget;

    public User(String name,int id){
        this.id = id;
        this.name = name;
        accounts = new ArrayList<>();
        monthlyBudget = 0;
    }
    public User(String name,int id,double monthlyBudget){
        this.id = id;
        this.name = name;
        this.accounts = new ArrayList<>();
        this.monthlyBudget = monthlyBudget;
    }

    public double getMonthlyBudget() {
        return monthlyBudget;
    }

    public void setMonthlyBudget(double money) {
        this.monthlyBudget = money;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
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

    public ArrayList<Account> getAccounts() {
        return accounts;
    }
}
