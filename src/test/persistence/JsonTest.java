package persistence;

import model.Spending;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkSpending(Spending Spending, String category, double amount, int day, int month, int year,
                                 int index) {
        assertEquals(category, Spending.getCategory());
        assertEquals(amount, Spending.getAmount());
        assertEquals(day, Spending.getDay());
        assertEquals(month, Spending.getMonth());
        assertEquals(year, Spending.getYear());
        assertEquals(index, Spending.getIndex());
    }
}
