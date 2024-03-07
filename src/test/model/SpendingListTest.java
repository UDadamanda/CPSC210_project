package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class SpendingListTest {
    private SpendingList testSpendingList;
    private Spending testSpendingA;
    private Spending testSpendingB;
    private Spending testSpendingC;

    @BeforeEach
    void setup() {
        testSpendingList = new SpendingList();
        testSpendingA = new Spending("a", 100.1, 1, 1, 1);
        testSpendingB = new Spending("b", 10.1, 2, 2, 2);
        testSpendingC = new Spending("c", 1.11, 3, 3, 3);
        testSpendingList.setName("abc");
    }

    @Test
    void testConstructor() {
        assertEquals(testSpendingList.getSize(), 0);
    }

    @Test
    void testSetName() {
        assertEquals("abc", testSpendingList.getName());
    }

    @Test
    void testAddSpending() {
        testSpendingList.addSpending(testSpendingA);
        assertEquals(1, testSpendingList.getSize());
        assertEquals(1,testSpendingList.getSpending(0).getIndex());
        assertEquals("a",testSpendingList.getSpending(0).getCategory());
        assertEquals(100.1, testSpendingList.getSpending(0).getAmount());
        assertEquals(1, testSpendingList.getSpending(0).getDay());
        assertEquals(1, testSpendingList.getSpending(0).getMonth());
        assertEquals(1, testSpendingList.getSpending(0).getYear());
    }

    @Test
    void testAddSpendingMultiple() {
        testSpendingList.addSpending(testSpendingA);
        testSpendingList.addSpending(testSpendingB);
        testSpendingList.addSpending(testSpendingC);
        assertEquals(3, testSpendingList.getSize());
    }

    @Test
    void testRemoveSpending() {
        testSpendingList.addSpending(testSpendingA);
        testSpendingList.addSpending(testSpendingB);
        testSpendingList.removeSpending(1);
        assertEquals(1, testSpendingList.getSize());
    }

    @Test
    void testRemoveSpendingMultiple() {
        testSpendingList.addSpending(testSpendingA);
        testSpendingList.addSpending(testSpendingB);
        testSpendingList.addSpending(testSpendingC);
        testSpendingList.removeSpending(1);
        testSpendingList.removeSpending(2);
        assertEquals(1, testSpendingList.getSize());
    }

    @Test
    void testToJsonEmpty() {
        JSONObject json = testSpendingList.toJson();
        assertEquals("abc", json.getString("name"));

        JSONArray testJsonArray = json.getJSONArray("spendingList");
        assertTrue(testJsonArray.isEmpty());
    }

    @Test
    void testToJson() {
        testSpendingList.addSpending(testSpendingA);
        testSpendingList.addSpending(testSpendingB);
        testSpendingList.addSpending(testSpendingC);

        JSONObject json = testSpendingList.toJson();
        assertEquals("abc", json.getString("name"));

        JSONArray testJsonArray = json.getJSONArray("spendingList");
        assertEquals(3, testJsonArray.length());
    }

    @Test
    void testGetAllSummary() {
        assertEquals(testSpendingList.getAllSummary(), new ArrayList<Spending>());
    }

    @Test
    void testGetAllSummaryMultiple() {
        testSpendingList.addSpending(testSpendingA);
        testSpendingList.addSpending(testSpendingB);
        testSpendingList.addSpending(testSpendingC);
        assertEquals(3, testSpendingList.getAllSummary().size());
    }



    @Test
    void testGetSpendingList() {
        testSpendingList.addSpending(testSpendingA);
        testSpendingList.addSpending(testSpendingB);
        testSpendingList.addSpending(testSpendingC);

        ArrayList<Spending> testList = testSpendingList.getSpendingList();
        assertEquals(3, testList.size());
        assertEquals(testSpendingA, testList.get(0));
        assertEquals(testSpendingB, testList.get(1));
        assertEquals(testSpendingC, testList.get(2));
    }
}
