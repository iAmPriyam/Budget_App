package accounts;

import database.SqliteDb;
import expenses.Expense;
import expenses.RegularExpense;
import incomes.Income;
import incomes.RegularIncome;

import java.util.ArrayList;
public class Account {

    private int id;
    private String accountName;
    private double accountBalance;
    private ArrayList<Expense> expensesList;
    private ArrayList<RegularExpense> regularExpensesList;
    private ArrayList<Income> incomesList;
    private ArrayList<RegularIncome> regularIncomesList;

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
        SqliteDb db = new SqliteDb();
        db.removeExpense(expense);
        db.closeConnection();
    }

    public void addIncome(Income income) {
        incomesList.add(income);
        this.increaseAccountBalance(income.getMoney());
    }

    public void removeIncome(Income income) {
        incomesList.remove(income);
        this.reduceAccountBalance(income.getMoney());
        SqliteDb db = new SqliteDb();
        db.removeIncome(income);
        db.closeConnection();
    }

    public void addRegularExpense(RegularExpense expense) {
        regularExpensesList.add(expense);
        this.reduceAccountBalance(expense.getPrice());
    }
    public void addRegularIncome(RegularIncome income) {
       regularIncomesList.add(income);
       this.increaseAccountBalance(income.getMoney());
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
    public ArrayList<RegularExpense> getRegularExpensesList() {
        return regularExpensesList;
    }
    public void setRegularExpensesList(ArrayList<RegularExpense> regularExpensesList) {
        this.regularExpensesList = regularExpensesList;
    }
    public ArrayList<RegularIncome> getRegularIncomesList() {
        return regularIncomesList;
    }
    public void setRegularIncomesList(ArrayList<RegularIncome> regularIncomesList) {
        this.regularIncomesList = regularIncomesList;
    }


    @Override
    public String toString() {
        return accountName + " " + accountBalance;
    }
}
