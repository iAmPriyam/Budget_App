import accounts.Account;
import expenses.Expense;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {
    @Test
    public void transferMoney() throws Exception {
        Account firstAcc = new Account("FirstAccount",1000);
        Account secondAcc = new Account("SecondAccount", 2000);
        secondAcc.transferMoney(100,firstAcc);
        assertEquals(1900,secondAcc.getAccountBalance(),0.001);
        assertEquals(1100,firstAcc.getAccountBalance(),0.001);
    }

    @Test
    public void increaseAccountBalance() throws Exception {
        Account acc = new Account("Test",1000.06);
        acc.increaseAccountBalance(567.09);
        assertEquals(1567.15,acc.getAccountBalance(),0.001);
    }

    @Test
    public void reduceAccountBalance() throws Exception {
        Account acc = new Account("Test",1000.06);
        acc.reduceAccountBalance(567.09);
        assertEquals(432.97,acc.getAccountBalance(),0.001);
    }
    @Test
    public void addExpenseGetExpensesList() throws Exception {
        Expense expense = new Expense("Tesco",150,"shopping");
        Account account = new Account("Test",1000);
        account.addExpense(expense);
        assertEquals(expense,account.getExpensesList().getLast());
        assertEquals(850,account.getAccountBalance(),0.001);
    }
}