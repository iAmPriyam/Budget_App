package accounts;

import expanses.Expanse;

import java.util.LinkedList;

public class Account {
    private String accountName;
    private double accountBalance;
    private LinkedList<Expanse> expansesList;

    public Account(String accountName, double accountBalance) {
        this.accountName = accountName;
        this.accountBalance = accountBalance;
        expansesList = new LinkedList<>();
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
    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }
}
