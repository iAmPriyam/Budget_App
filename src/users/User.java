package users;

import accounts.Account;
import expenses.Expense;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class User {
    private static User user;

    private int id;
    private String name;
    private ArrayList<Account> accounts;
    private double monthlyBudget;


    private User(String name,int id,double monthlyBudget){
        this.id = id;
        this.name = name;
        this.accounts = new ArrayList<>();
        this.monthlyBudget = monthlyBudget;
    }

    public static void initializeUser(String name,int id,double monthlyBudget) {
        user = new User(name,id,monthlyBudget);
    }
    public static User getInstance() {
        return user;
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
    public ArrayList<Expense> getMonthlyExpenses(){
        ArrayList<Expense> expenses = new ArrayList<>();
        GregorianCalendar date = new GregorianCalendar();
        int month = date.get(Calendar.MONTH);
        for(Account account: accounts) {
            for(Expense expense: account.getExpensesList()) {
                if(expense.getDate().get(Calendar.MONTH)==month+1) {
                    expenses.add(expense);
                }
            }
        }
        return expenses;
    }

    public ArrayList<Expense> getMonthlyExpenses(int month){
        ArrayList<Expense> expenses = new ArrayList<>();
        for(Account account: accounts) {
            for(Expense expense: account.getExpensesList()) {
                if(expense.getDate().get(Calendar.MONTH)==month+1) {
                    expenses.add(expense);
                }
            }
        }
        return expenses;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", accounts=" + accounts +
                ", monthlyBudget=" + monthlyBudget +
                '}';
    }
}
