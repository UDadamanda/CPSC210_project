package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SpendingTest {
    private Spending testSpending1;
    private Spending testSpending2;

    @BeforeEach
    void setup() {
        testSpending1 = new Spending("Personal", 100000.1, 31, 12, 20000);
        testSpending2 = new Spending("something", 1.1, 1, 1, 1);
    }

    @Test
    void testConstructorLarge() {
        assertEquals(testSpending1.getCategory(), "Personal");
        assertEquals(testSpending1.getAmount(), 100000.1);
        assertEquals(testSpending1.getDay(), 31);
        assertEquals(testSpending1.getMonth(), 12);
        assertEquals(testSpending1.getYear(), 20000);
    }

    @Test
    void testConstructorSmall() {
        assertEquals(testSpending2.getCategory(), "something");
        assertEquals(testSpending2.getAmount(), 1.1);
        assertEquals(testSpending2.getDay(), 1);
        assertEquals(testSpending2.getMonth(), 1);
        assertEquals(testSpending2.getYear(), 1);
    }

    @Test
    void testSetIndexSmall() {
        testSpending1.setIndex(1);
        assertEquals(testSpending1.getIndex(),1);
    }

    @Test
    void testSetIndexLarge() {
        testSpending1.setIndex(100000);
        assertEquals(testSpending1.getIndex(),100000);
    }

    @Test
    void testDisplaySpending() {
        assertEquals("0. 20000/12/31 Personal $100000.1", testSpending1.displaySpending());
    }

    @Test
    void testDisplaySpendingIndex() {
        testSpending1.setIndex(100);
        assertEquals("100. 20000/12/31 Personal $100000.1", testSpending1.displaySpending());
    }

    @Test
    void testToJson() {
        JSONObject json = testSpending1.toJson();
        assertEquals("Personal", json.getString("category"));
        assertEquals(100000.1, json.getDouble("amount"));
        assertEquals(31, json.getInt("day"));
        assertEquals(12, json.getInt("month"));
        assertEquals(20000, json.getInt("year"));
        assertEquals(0, json.getInt("index"));
    }


}