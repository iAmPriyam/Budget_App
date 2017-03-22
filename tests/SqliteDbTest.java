import accounts.Account;
import database.SqliteDb;
import expenses.Expense;
import incomes.Income;
import org.junit.Test;
import users.User;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ListIterator;

import static org.junit.Assert.*;

public class SqliteDbTest {
    @Test
    public void updateAccount() throws Exception {
        SqliteDb db = new SqliteDb();
        Account account= new Account(3,"NewTestName123123123",9999.99);
        db.updateAccount(account);
    }

    @Test
    public void insertAccount() throws Exception {
        SqliteDb db = new SqliteDb();
        db.insertAccount(1,"TESTACCountNAME",1987.90);
    }

    @Test
    public void insertExpense() throws Exception {
        SqliteDb db = new SqliteDb();
        db.insertExpense(1,1,"TestWydatku",129.49);
    }

    @Test
    public void isDbConnected1() throws Exception {
        SqliteDb db = new SqliteDb();
        assertEquals(true,db.isDbConnected());
    }

    @Test
    public void isPasswordCorrect1() throws Exception {
        SqliteDb db = new SqliteDb();
        assertEquals(true,db.isPasswordCorrect("budget"));
    }


    @Test
    public void getUserData() throws Exception {
        SqliteDb db = new SqliteDb();
        User user = db.getUserData("budget");
        ArrayList<Account> accounts = user.getAccounts();
        ListIterator<Account> iterator = accounts.listIterator();
        System.out.println(user.getName() + " budget:  " + user.getMonthlyBudget());
        while(iterator.hasNext()) {
            Account acc = iterator.next();
            System.out.println(acc.getAccountName() + " " + acc.getAccountBalance() );
            System.out.println("wydatki: ");
            ArrayList<Expense> expenses = acc.getExpensesList();
            ListIterator<Expense> expenseListIterator = expenses.listIterator();
            while(expenseListIterator.hasNext()) {
                Expense expense = expenseListIterator.next();
                System.out.println(expense.getDate().get(Calendar.DAY_OF_MONTH)+"."+expense.getDate().get(Calendar.MONTH)+"."+expense.getDate().get(Calendar.YEAR));
                System.out.println("----");
            }
            ArrayList<Income> incomes = acc.getIncomesList();
            ListIterator<Income> incomesListIterator = incomes.listIterator();
            while(incomesListIterator.hasNext()) {
                Income income = incomesListIterator.next();
                System.out.println(income.getName()+ " :" + income.getMoney() + " " + income.getCategory().getName());
            }

        }
    }

}