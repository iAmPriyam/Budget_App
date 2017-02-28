import org.junit.Test;
import expanses.*;

import static org.junit.Assert.*;

public class ExpanseTest {
    @Test
    public void setCategory() throws Exception {
        Expanse test = new Expanse("expanse",150,"food");
        ExpanseCategory newCategory = new ExpanseCategory("education");
        test.setCategory(newCategory);
        assertEquals("education",test.getCategory().getName());
    }

    @Test
    public void setCategory1() throws Exception {
        Expanse test = new Expanse("expanse",150,"food");
        test.setCategory("education");
        assertEquals("education",test.getCategory().getName());
    }

}