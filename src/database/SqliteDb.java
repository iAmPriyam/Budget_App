package database;

import accounts.Account;
import users.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class SqliteDb {

    private Connection connection;

    public SqliteDb() {
        this.connection = SqliteConnection.Connector();
        if(connection==null) {
            System.exit(2);
        }
    }

    public boolean isDbConnected() {
        try{
            return !connection.isClosed();
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isPasswordCorrect(String password) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM users WHERE password = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,password);

            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println(e);
            return false;
        } finally {
            preparedStatement.close();
            resultSet.close();
        }
    }
    public User getUserData(String name) throws SQLException{
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM users WHERE name = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,name);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                User user = new User(resultSet.getString("name"),resultSet.getInt("id"));
                LinkedList<Account> accounts = this.getAccounts(user.getId());
                user.setAccounts(accounts);
                return user;
            }
            else {
                return null;
            }
        } catch (Exception e) {
            System.out.println(e);
            return null;
        } finally {
            preparedStatement.close();
            resultSet.close();
        }
    }

    private LinkedList<Account> getAccounts(int id) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM accounts where user_id = ?";
        String userId = Integer.toString(id);
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,userId);
            resultSet = preparedStatement.executeQuery();
            LinkedList<Account> accounts = new LinkedList<>();
            while (resultSet.next()) {
                int accountId = resultSet.getInt("id");
                String accountName = resultSet.getString("name");
                double accountBalance = resultSet.getDouble("balance");
                Account account = new Account(accountName,accountBalance,accountId);
                accounts.add(account);
            }
            return accounts;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        } finally {
            preparedStatement.close();
            resultSet.close();
        }
    }
}
