package incomes;

import accounts.Account;
import database.SqliteDb;
import users.User;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class RegularIncome extends Income  {
    private int frequency;
    private int accountId;
    private GregorianCalendar lastIncome;

    public RegularIncome(int id, String name, double money, IncomeCategory category,
                         GregorianCalendar date, int frequency, int accountId, GregorianCalendar lastIncome) {
        super(id,name,money,category, date);
        this.frequency = frequency;
        this.accountId = accountId;
        this.lastIncome = lastIncome;
    }

    public void isIncomeTime() {
        GregorianCalendar now = new GregorianCalendar();
        if(daysBetween(now)>=frequency) {
            for(Account account: User.getInstance().getAccounts()) {
                if(account.getId() == accountId) {
                    account.increaseAccountBalance(this.getMoney());
                    this.lastIncome = now;
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
        int to = lastIncome.get(Calendar.DAY_OF_YEAR);
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

    public GregorianCalendar getLastIncome() {
        return lastIncome;
    }

    public void setLastIncome(GregorianCalendar lastIncome) {
        this.lastIncome = lastIncome;
    }

    @Override
    public String toString() {
        return super.toString() + "RegularIncome{" +
                "frequency=" + frequency +
                ", accountId=" + accountId +
                ", lastIncome=" + lastIncome +
                '}';
    }
}
