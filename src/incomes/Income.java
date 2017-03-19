package incomes;

import java.util.GregorianCalendar;

/**
 * Created by Kura on 19.03.2017.
 */
public class Income {
    private int id;
    private String name;
    private double money;
    private GregorianCalendar date;
    private IncomeCategory category;

    public Income(int id, String name, double money, IncomeCategory category, GregorianCalendar date) {
        this.id = id;
        this.name = name;
        this. money = money;
        this.category = category;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public GregorianCalendar getDate() {
        return date;
    }

    public void setDate(GregorianCalendar date) {
        this.date = date;
    }

    public IncomeCategory getCategory() {
        return category;
    }

    public void setCategory(IncomeCategory category) {
        this.category = category;
    }
}
