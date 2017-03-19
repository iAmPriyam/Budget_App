package expenses;

public class ExpenseCategory {
    private int id;
    private String name;
    public ExpenseCategory(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
