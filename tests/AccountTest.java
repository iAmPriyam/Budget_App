import accounts.Account;
import expenses.Expense;
import incomes.Income;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * JUnit tests for Account class
 */
public class AccountTest {
    @Test
    public void transferMoney() throws Exception {
        Account account_1 = new Account(1,"Account_1",1500.23);
        Account account_2 = new Account(2,"Account_2", 1000);
        account_1.transferMoney(1000.23,account_2);
        assertEquals(500,account_1.getAccountBalance(),1);
        assertEquals(2000,account_2.getAccountBalance(),1);
        account_1.transferMoney(500,account_1);
        assertEquals(500,account_1.getAccountBalance(),1);
    }

    @Test
    public void increaseAccountBalance() throws Exception {
        Account account = new Account(1,"Account",1500);
        account.increaseAccountBalance(1000.21);
        assertEquals(2500.21,account.getAccountBalance(),1);
    }

    @Test
    public void reduceAccountBalance() throws Exception {
        Account account = new Account(1,"Account",1500);
        account.reduceAccountBalance(500);
        assertEquals(1000,account.getAccountBalance(),1);
    }

    @Test
    public void addExpense() throws Exception {
        Account account = new Account(1,"Account", 1500);
        Expense expense = new Expense(1,"Expense",100);
        account.addExpense(expense);
        assertEquals(true, account.getExpensesList().contains(expense));
        assertEquals(1400,account.getAccountBalance(),1);
    }

    @Test
    public void removeExpense() throws Exception {
        Account account = new Account(1,"Account", 1500);
        Expense expense = new Expense(1,"Expense",100);
        account.addExpense(expense);
        account.removeExpense(expense);
        assertEquals(false,account.getExpensesList().contains(expense));
        assertEquals(1500,account.getAccountBalance(),1);
    }

    @Test
    public void addIncome() throws Exception {
        Account account = new Account(1,"Account",1500);
        Income income = new Income(1,"Income",1000);
        account.addIncome(income);
        assertEquals(true,account.getIncomesList().contains(income));
        assertEquals(2500,account.getAccountBalance(),1);
    }

    @Test
    public void removeIncome() throws Exception {
        Account account = new Account(1,"Account", 1500);
        Income income = new Income(1,"Expense",100);
        account.addIncome(income);
        account.removeIncome(income);
        assertEquals(false,account.getIncomesList().contains(income));
        assertEquals(1500,account.getAccountBalance(),1);
    }
}