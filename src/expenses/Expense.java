package expenses;


import java.util.GregorianCalendar;

public class Expense {
    private String name;
    private double price;
    private GregorianCalendar date;
    private ExpenseCategory category;

    public GregorianCalendar getDate() {
        return date;
    }

    public void setDate(GregorianCalendar date) {
        this.date = date;
    }

    public Expense(String name, double price, ExpenseCategory category) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.date = new GregorianCalendar();

    }
    public Expense(String name, double price, String category) {
        this.name = name;
        this.price = price;
        ExpenseCategory cat = new ExpenseCategory(category);
        this.category = cat;
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

    public ExpenseCategory getCategory() {
        return category;
    }

    public void setCategory(ExpenseCategory category) {
        this.category = category;
    }
    public void setCategory(String category) {
        ExpenseCategory cat = new ExpenseCategory(category);
        this.category = cat;
    }
}
