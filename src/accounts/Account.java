package accounts;

import expenses.Expense;
import incomes.Income;

import java.util.ArrayList;
public class Account {

    private int id;
    private String accountName;
    private double accountBalance;
    private ArrayList<Expense> expensesList;
    private ArrayList<Income> incomesList;

    public Account( int id, String accountName, double accountBalance) {
        this.accountName = accountName;
        this.accountBalance = accountBalance;
        this.id = id;
        this.expensesList = new ArrayList<>();
        this.incomesList = new ArrayList<>();
    }

    public void transferMoney(double money, Account targetAccount) {
        targetAccount.increaseAccountBalance(money);
        this.reduceAccountBalance(money);
    }

    public void increaseAccountBalance(double money) {
        accountBalance += money;
    }

    public void reduceAccountBalance(double money) {
        accountBalance -= money;
    }

    public void addExpense(Expense expense) {
        expensesList.add(expense);
        this.reduceAccountBalance(expense.getPrice());
    }

    public void removeExpense(Expense expense) {
        expensesList.remove(expense);
        this.increaseAccountBalance(expense.getPrice());
    }

    public void addIncome(Income income) {
        incomesList.add(income);
        this.increaseAccountBalance(income.getMoney());
    }

    public void removeIncome(Income income) {
        incomesList.remove(income);
        this.reduceAccountBalance(income.getMoney());
    }

    public int getId() {
        return id;
    }
    public String getAccountName() {
        return accountName;
    }
    public double getAccountBalance() {
        return accountBalance;
    }
    public ArrayList<Expense> getExpensesList() {
        return expensesList;
    }
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }
    public void setExpensesList(ArrayList<Expense> expensesList) {
        this.expensesList = expensesList;
    }
    public ArrayList<Income> getIncomesList() {
        return incomesList;
    }
    public void setIncomesList(ArrayList<Income> incomesList) {
        this.incomesList = incomesList;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", accountName='" + accountName + '\'' +
                ", accountBalance=" + accountBalance +
                ", expensesList=" + expensesList +
                ", incomesList=" + incomesList +
                '}';
    }
}
