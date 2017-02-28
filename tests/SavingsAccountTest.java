import accounts.SavingsAccount;
import org.junit.Test;

import java.util.GregorianCalendar;

import static org.junit.Assert.*;

public class SavingsAccountTest {
    @Test
    public void capitalizeInterest() throws Exception {
        SavingsAccount test = new SavingsAccount("testAcc",1000,0.06,2,new GregorianCalendar(2016,2,14));
        test.capitalizeInterest();
        assertEquals(1060.90,test.getAccountBalance(),0.001);
    }

}