package database;

import accounts.Account;
import expenses.Expense;
import users.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SqliteDb {

    private Connection connection;

    /**
     * SqliteDB constructor - connecting to database via SqliteConnection class
     */
    public SqliteDb() {
        this.connection = SqliteConnection.connector();
        if(connection==null) {
            System.exit(1);
        }
    }

    /**
     * Checking if application is connected to database
     * @return true - connected or false - not connected
     * */
    public boolean isDbConnected() {
        try{
            return !connection.isClosed();
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Verifies password
     * @param password password to verify
     * @return true - password correct false - password incorrect
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

    //DATABASE GETTERS
    /**
     * Getting user data based on password and filling User object attributes
     * @param password
     * @return User object with all attributes that are stored in database
     */
    public User getUserData(String password) {
        String query = "SELECT * FROM users WHERE password = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                User user = new User(resultSet.getString("name"),resultSet.getInt("id"));
                ArrayList<Account> accounts = this.getAccounts(user.getId());
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
    /**
     * Getting user's Account. Account's are connected to user by ids
     * @param user_id user's id
     * @return ArrayList of all user's account
     */
    private ArrayList<Account> getAccounts(int user_id) {
        String query = "SELECT * FROM accounts where user_id = ?";
        String userId = Integer.toString(user_id);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Account> accounts = new ArrayList<>();
            while (resultSet.next()) {
                int accountId = resultSet.getInt("id");
                String accountName = resultSet.getString("name");
                double accountBalance = resultSet.getDouble("balance");
                Account account = new Account(accountId, accountName,accountBalance);
                account.setExpensesList(this.getAccountExpenses(account.getId()));
                accounts.add(account);
            }
            return accounts;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }
    /**
     *
     * @param id
     * @return
     */
    private ArrayList<Expense> getAccountExpenses(int id) {
        String query = "SELECT * FROM expenses WHERE account_id = ?";
        String accountId = Integer.toString(id);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,accountId);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Expense> expenses = new ArrayList<>();
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
    //DATABASE INSERTS
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

    //DATABASE UPDATE

    /**
     * updates accounts table's row in database
     * @param account account that should be updated
     */
    public void updateAccount(Account account) {
        String string = "UPDATE accounts SET name = ?, balance = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(string);
            preparedStatement.setString(1,account.getAccountName());
            preparedStatement.setDouble(2,account.getAccountBalance());
            preparedStatement.setInt(3,account.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException exc) {
            System.out.println(exc);
        }
    }
    /**
     * Updates expenses table's row in database
     * @param expense expense that should be updated
     */
    public void updateExpense(Expense expense) {
        String sql = "UPDATE expense SET name = ?, price = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,expense.getName());
            preparedStatement.setDouble(2,expense.getPrice());
            preparedStatement.setInt(3,expense.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    /**
     * Updates users table's row in database
     * @param user User that should be updated
     */
    public void updateUser(User user) {
        String s = "UPDATE users SET name = ?, monthlyBudget = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(s);
            preparedStatement.setString(1,user.getName());
            preparedStatement.setDouble(2,user.getMonthlyBudget());
            preparedStatement.setInt(3,user.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException exc) {
            System.out.println(exc);
        }
    }

    //TODO JAVADOCS
}
