package database;

import users.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        String query = "select * from users where password = ?";
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
        String query = "select * from users where name = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,name);

            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                //System.out.println(resultSet.getString("name"));
                User user = new User(resultSet.getString("name"));
                System.out.println(user.getName());
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
}
