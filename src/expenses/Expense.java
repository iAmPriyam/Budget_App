package expenses;
import java.util.GregorianCalendar;

public class Expense {
    private int id;
    private String name;
    private double price;
    private GregorianCalendar date;
    private ExpenseCategory category;

    public Expense(int id, String name, double price, ExpenseCategory category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.date = new GregorianCalendar();
    }

    public Expense(int id, String name, double price, ExpenseCategory category, GregorianCalendar date) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.date = date;
    }

    public int getId() {
        return id;
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

    public ExpenseCategory getCategory() {
        return category;
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

    public void setCategory(ExpenseCategory category) {
        this.category = category;
    }
}
