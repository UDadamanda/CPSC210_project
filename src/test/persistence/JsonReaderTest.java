package persistence;

import model.Spending;
import model.SpendingList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {

        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            SpendingList sl = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptySpendingList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptySpendingList.json");
        try {
            SpendingList spendingList = reader.read();
            assertEquals("My Spending List", spendingList.getName());
            assertEquals(0, spendingList.getSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralSpendingList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralSpendingList.json");
        try {
            SpendingList spendingList = reader.read();
            assertEquals("My Spending List", spendingList.getName());
            ArrayList<Spending> testSpendingList = spendingList.getSpendingList();
            assertEquals(2, testSpendingList.size());
            checkSpending(testSpendingList.get(0),"abc", 1000, 23, 1, 2023, 1);
            checkSpending(testSpendingList.get(1), "efg", 2000, 24, 2, 2024, 2);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
