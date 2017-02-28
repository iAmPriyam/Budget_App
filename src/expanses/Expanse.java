package expanses;


public class Expanse {
    private String name;
    private double price;
    private ExpanseCategory category;

    public Expanse(String name, double price, ExpanseCategory category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }
    public Expanse(String name, double price, String category) {
        this.name = name;
        this.price = price;
        ExpanseCategory cat = new ExpanseCategory(category);
        this.category = cat;
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

    public ExpanseCategory getCategory() {
        return category;
    }

    public void setCategory(ExpanseCategory category) {
        this.category = category;
    }
    public void setCategory(String category) {
        ExpanseCategory cat = new ExpanseCategory(category);
        this.category = cat;
    }
}
