package expenses;

import accounts.Account;
import database.SqliteDb;
import users.User;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Kura on 11.06.2017.
 */
public class RegularExpense extends Expense {
    private int frequency;
    private int accountId;
    private GregorianCalendar lastExpense;

    public RegularExpense(int id, String name, double price, ExpenseCategory category, GregorianCalendar date,
                          int frequency, int accountId, GregorianCalendar lastExpense) {
        super(id,name,price,category,date);
        this.frequency = frequency;
        this.accountId = accountId;
        this.lastExpense = lastExpense;
    }

    public void isExpenseTime() {
        GregorianCalendar date = new GregorianCalendar();
        if(daysBetween(date)>=frequency) {
            for(Account account: User.getInstance().getAccounts()) {
                if(account.getId() == this.accountId) {
                    account.increaseAccountBalance(this.getPrice());
                    this.lastExpense = date;
                    SqliteDb db = new SqliteDb();
                    db.updateAccount(account);
                    db.closeConnection();
                    break;
                }
            }
        }
    }

    public int daysBetween(GregorianCalendar currentDate) {
        int from = currentDate.get(Calendar.DAY_OF_YEAR);
        int to = lastExpense.get(Calendar.DAY_OF_YEAR);
        return from - to;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public GregorianCalendar getLastExpense() {
        return lastExpense;
    }

    public void setLastExpense(GregorianCalendar lastExpense) {
        this.lastExpense = lastExpense;
    }
}
