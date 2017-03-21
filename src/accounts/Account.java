package accounts;

import expenses.Expense;
import incomes.Income;

import java.util.ArrayList;
/**
 * Account object has 4 attributes:
 * - id - unique id of Account,
 * - accountName - name of account,
 * - accountBalance - account balance,
 * - expensesList - ArrayList of expenses.
 */
public class Account {

    private int id;
    private String accountName;
    private double accountBalance;
    private ArrayList<Expense> expensesList;
    private ArrayList<Income> incomesList;

    /**
     * Account class constructor
     * @param id
     * @param accountName
     * @param accountBalance
     */
    public Account( int id, String accountName, double accountBalance) {
        this.accountName = accountName;
        this.accountBalance = accountBalance;
        this.id = id;
        expensesList = new ArrayList<>();
        incomesList = new ArrayList<>();
    }
    /**
     * Transfers money from account to targetAccount.
     * @param money amount of money to transform.
     * @param targetAccount reference of target account.
     */
    public void transferMoney(double money, Account targetAccount) {
        targetAccount.increaseAccountBalance(money);
        this.reduceAccountBalance(money);
    }
    /**
     * Increases account balance
     * @param money amount of money that should be added to accountBalance
     */
    public void increaseAccountBalance(double money) {
        accountBalance += money;
    }
    /**
     * Reduces account balance
     * @param money amount of money that should be taken from accountBalance
     */
    public void reduceAccountBalance(double money) {
        accountBalance -= money;
    }
    /**
     * Adding Expense object to expensesList<Expense> and reduces accountBalance
     * @param expense new expense that should be added to expenseList
     */
    public void addExpense(Expense expense) {
        expensesList.add(expense);
        this.reduceAccountBalance(expense.getPrice());
    }
    /**
     * Removes expense from expense list and increase accountBalance
     * @param expense Expense object to remove from list
     */
    public void removeExpense(Expense expense) {
        expensesList.remove(expense);
        this.increaseAccountBalance(expense.getPrice());
    }
    /**
     * Adds new income to the end of incomeList and increases accountBalance
     * @param income income Object
     */
    public void addIncome(Income income) {
        incomesList.add(income);
        this.increaseAccountBalance(income.getMoney());
    }
    /**
     * Removes income from incomesList and reduces account's balance
     * @param income
     */
    public void removeIncome(Income income) {
        incomesList.remove(income);
        this.reduceAccountBalance(income.getMoney());
    }
    //Getters and setters
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
}
