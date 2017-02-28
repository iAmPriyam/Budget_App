package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginModel {
    Connection connection;
    public LoginModel() {
        connection = SqliteConnection.Connector();
        if(connection==null) {
            System.exit(1);
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

    public boolean isLogin(String password) throws SQLException{
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select * from users where password = ?";
        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,password);

            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return true;
            } else {
                return  false;
            }

        }catch (Exception e) {
            System.out.println(e);
            return false;
        } finally {
            preparedStatement.close();
            resultSet.close();
        }
    }
}
