package accounts;

import expenses.Expense;

import java.util.LinkedList;

public class Account {
    private String accountName;
    private double accountBalance;
    private int id;
    private LinkedList<Expense> expensesList;

    public Account(String accountName, double accountBalance, int id) {
        this.accountName = accountName;
        this.accountBalance = accountBalance;
        this.id = id;
        expensesList = new LinkedList<>();
    }

    public int getId() {
        return id;
    }

    public void transferMoney(double money, Account targetAccount) {
        targetAccount.increaseAccountBalance(money);
        this.reduceAccountBalance(money);
    }
    public void increaseAccountBalance(double money) {
        accountBalance += money;
    }

    public void reduceAccountBalance(double expanse) {
        accountBalance -= expanse;
    }

    public void addExpense(Expense expense) {
        expensesList.add(expense);
        this.reduceAccountBalance(expense.getPrice());
    }

    public LinkedList<Expense> getExpensesList() {
        return expensesList;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setExpensesList(LinkedList<Expense> expensesList) {
        this.expensesList = expensesList;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }
}
