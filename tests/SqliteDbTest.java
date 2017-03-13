import accounts.Account;
import database.SqliteDb;
import expenses.Expense;
import groovy.sql.Sql;
import org.junit.Test;
import users.User;

import static org.junit.Assert.*;

public class SqliteDbTest {
    @Test
    public void insertAccount() throws Exception {
        SqliteDb db = new SqliteDb();
        db.insertAccount(1,"Gownogowno",1987.90);
    }

    @Test
    public void insertExpense() throws Exception {
        SqliteDb db = new SqliteDb();
        Expense exp = new Expense(5,"test",158.49);
        db.insertExpense(1,"TestWydatku",129.49);
    }

    @Test
    public void isDbConnected1() throws Exception {
        SqliteDb db = new SqliteDb();
        assertEquals(true,db.isDbConnected());
    }

    @Test
    public void isPasswordCorrect1() throws Exception {
        SqliteDb db = new SqliteDb();
        assertEquals(true,db.isPasswordCorrect("test"));
    }


    @Test
    public void getUserData() throws Exception {

    }

}