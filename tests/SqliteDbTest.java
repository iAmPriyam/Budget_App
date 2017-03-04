import database.SqliteDb;
import org.junit.Test;
import users.User;

import static org.junit.Assert.*;

/**
 * Created by Kura on 04.03.2017.
 */
public class SqliteDbTest {
    @Test
    public void isDbConnected() throws Exception {
        SqliteDb db = new SqliteDb();
        assertEquals(true,db.isDbConnected());
    }

    @Test
    public void isPasswordCorrect() throws Exception {
        SqliteDb db = new SqliteDb();
        assertEquals(true,db.isPasswordCorrect("test"));
    }

    @Test
    public void getUserData() throws Exception {
        SqliteDb db = new SqliteDb();
        User user = db.getUserData("mike");
        assertEquals("mike",user.getName());
    }

}