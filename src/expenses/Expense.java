package expenses;
import java.util.GregorianCalendar;

public class Expense {
    private String name;
    private double price;
    private GregorianCalendar date;

    public Expense(String name, double price) {
        this.name = name;
        this.price = price;
        this.date = new GregorianCalendar();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public GregorianCalendar getDate() {
        return date;
    }

    public void setDate(GregorianCalendar date) {
        this.date = date;
    }
}
