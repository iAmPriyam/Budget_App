package database;

import java.sql.*;
/**
 * SqliteConnection is responsible to connect to db
 */
public class SqliteConnection {
    /**
     * Connecting to application database
     * @return Connection model(if connection succeeded) or null.
     */
    public static Connection connector() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:database.db");
            return conn;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }
}
