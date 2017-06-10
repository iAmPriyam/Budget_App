package accounts;

import org.codehaus.groovy.runtime.powerassert.SourceText;
import sun.util.calendar.Gregorian;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class SavingAccount extends Account {
    private double interest;
    private GregorianCalendar lastCapitalizationDate;
    private int capitalizationPeriod; //in months

    public SavingAccount(int id, String accountName, double accountBalance, double interest, GregorianCalendar lastCapitalizationDate, int capitalizationPeriod) {
        super(id,accountName,accountBalance);
        this.interest = interest;
        this.lastCapitalizationDate = lastCapitalizationDate;
        this.capitalizationPeriod = capitalizationPeriod;
    }

    public void capitalizationCheck(){
        GregorianCalendar date = new GregorianCalendar();
        if(date.get(Calendar.MONTH) - lastCapitalizationDate.get(Calendar.MONTH) == capitalizationPeriod) {
            this.capitalize();
            System.out.println("startuje kapitalizacje");
            this.lastCapitalizationDate = date;
        }
    }
    public void capitalize() {
        this.setAccountBalance(this.getAccountBalance()+this.getAccountBalance()*interest);
    }

//    @Override
//    public String toString() {
//        return super.toString() + "SavingAccount{" +
//                "interest=" + interest +
//                ", lastCapitalizationDate=" + lastCapitalizationDate +
//                ", capitalizationPeriod=" + capitalizationPeriod +
//                '}';
//    }
}
