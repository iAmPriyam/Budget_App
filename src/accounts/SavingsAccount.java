package accounts;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.lang.Math;


public class SavingsAccount extends Account {
    private double interest;
    private int capitalizationsPerYear;
    private GregorianCalendar openingDate;


    public SavingsAccount(String accountName, double accountBalance,int id, double interest, int capitalizationsPerYear, GregorianCalendar date) {
        super(id, accountName ,accountBalance);
        this.interest = interest;
        this.capitalizationsPerYear = capitalizationsPerYear;
        this.openingDate = date;
    }

    public void capitalizeInterest(){
        GregorianCalendar now = new GregorianCalendar();
        now.set(Calendar.MONTH, -12);
        if(!openingDate.before(now)) {
            double newBalance = getAccountBalance()*Math.pow((1+(interest/capitalizationsPerYear)),capitalizationsPerYear);
            this.setAccountBalance(newBalance);
        }
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public int getCapitalizationsPerYear() {
        return capitalizationsPerYear;
    }

    public void setCapitalizationsPerYear(int capitalizationsPerYear) {
        this.capitalizationsPerYear = capitalizationsPerYear;
    }

    public GregorianCalendar getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(GregorianCalendar openingDate) {
        this.openingDate = openingDate;
    }
}
