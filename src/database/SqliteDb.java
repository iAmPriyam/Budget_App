package database;

import accounts.Account;
import expenses.Expense;
import users.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class SqliteDb {

    private Connection connection;
    /*
    connecting to db
     */
    public SqliteDb() {
        this.connection = SqliteConnection.Connector();
        if(connection==null) {
            System.exit(2);
        }
    }
    /*
    checking if the the program is connected to db
     */
    public boolean isDbConnected() {
        try{
            return !connection.isClosed();
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    /*
    Checking if password is correct
     */
    public boolean isPasswordCorrect(String password) {
        String query = "SELECT * FROM users WHERE password = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }
    /*
    Getting user data
    */
    public User getUserData(String password) {
        String query = "SELECT * FROM users WHERE password = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                User user = new User(resultSet.getString("name"),resultSet.getInt("id"));
                LinkedList<Account> accounts = this.getAccounts(user.getId());
                user.setAccounts(accounts);
                return user;
            }
            else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }
    /*
    Getting user's accounts
     */
    public LinkedList<Account> getAccounts(int id) {
        String query = "SELECT * FROM accounts where user_id = ?";
        String userId = Integer.toString(id);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            LinkedList<Account> accounts = new LinkedList<>();
            while (resultSet.next()) {
                int accountId = resultSet.getInt("id");
                String accountName = resultSet.getString("name");
                double accountBalance = resultSet.getDouble("balance");
                Account account = new Account(accountName,accountBalance,accountId);
                account.setExpensesList(this.getAccountExpenses(account.getId()));
                accounts.add(account);
            }
            return accounts;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }
    /*
    Getting expenses
     */
    public LinkedList<Expense> getAccountExpenses(int id) {
        String query = "SELECT * FROM expenses WHERE account_id = ?";
        String accountId = Integer.toString(id);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,accountId);
            ResultSet resultSet = preparedStatement.executeQuery();
            LinkedList<Expense> expenses = new LinkedList<>();
            while(resultSet.next()) {
                int expenseId = resultSet.getInt("id");
                String expenseName = resultSet.getString("name");
                double expansePrice = resultSet.getDouble("price");
                Expense expense = new Expense(expenseId,expenseName,expansePrice);
                expenses.add(expense);
            }
            return expenses;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    //TODO singe method to insert rows into database
    public void insertAccount(int userId, String accountName, double accountBalance) {
        String sql = "INSERT INTO accounts (user_id,name,balance) VALUES (?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,userId);
            preparedStatement.setString(2,accountName);
            preparedStatement.setDouble(3,accountBalance);
            preparedStatement.executeUpdate();
        } catch (SQLException exc) {
            System.out.println(exc);
        }
    }

    public void insertExpense(int accountId, String expenseName, double expensePrice) {
        String sql = "INSERT INTO expenses (account_id,name,price) VALUES (?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,accountId);
            preparedStatement.setString(2,expenseName);
            preparedStatement.setDouble(3,expensePrice);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException exc) {
            System.out.println(exc);
        }
    }



}
