import org.junit.Test;
import expenses.*;

import static org.junit.Assert.*;

public class ExpenseTest {
    @Test
    public void setCategory() throws Exception {
        Expense test = new Expense("expense",150,"food");
        ExpenseCategory newCategory = new ExpenseCategory("education");
        test.setCategory(newCategory);
        assertEquals("education",test.getCategory().getName());
    }

    @Test
    public void setCategory1() throws Exception {
        Expense test = new Expense("expense",150,"food");
        test.setCategory("education");
        assertEquals("education",test.getCategory().getName());
    }

}