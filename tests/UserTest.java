import accounts.Account;
import org.junit.Test;
import users.User;

import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void addAndGetAccount() throws Exception {
        User testUser = new User("Tom",1);
        Account accounts[] = new Account[2];
        accounts[0] = new Account(1,"testAcc",1000);
        accounts[1] = new Account(2,"testAcc2",2000);
        for(int i=0;i<2;i++) {
            testUser.addAccount(accounts[i]);
        }
        for(int i=0;i<2;i++) {
            assertEquals(accounts[i],testUser.getAccounts().get(i));
        }
    }

    @Test
    public void deleteAccount() throws Exception {
        User testUser = new User("Tom",1);
        Account accounts[] = new Account[2];
        accounts[0] = new Account(1,"testAcc",1000);
        accounts[1] = new Account(2,"testAcc2",1000);

        for(int i=0;i<2;i++) {
            testUser.addAccount(accounts[i]);
        }

        testUser.deleteAccount(accounts[1]);
        //assertEquals(accounts[0],testUser.getAccounts().get());
    }
}