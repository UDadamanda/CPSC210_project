package persistence;

import model.Spending;
import model.SpendingList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {
    @Test
    void testWriterInvalidFile() {
        try {
            SpendingList spendingList = new SpendingList();
            spendingList.setName("My Spending List");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptySpendingList() {
        try {
            SpendingList sl = new SpendingList();
            sl.setName("My Spending List");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptySpendingList.json");
            writer.open();
            writer.write(sl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptySpendingList.json");
            sl = reader.read();
            assertEquals("My Spending List", sl.getName());
            assertEquals(0, sl.getSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralSpendingList() {
        try {
            SpendingList sl = new SpendingList();
            sl.setName("My Spending List");
            sl.addSpending(new Spending("abc", 1000, 23, 1, 2023));
            sl.addSpending(new Spending("efg", 2000, 24, 2, 2024));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralSpendingList.json");
            writer.open();
            writer.write(sl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralSpendingList.json");
            sl = reader.read();
            assertEquals("My Spending List", sl.getName());
            List<Spending> spendingList = sl.getSpendingList();
            assertEquals(2, spendingList.size());
            checkSpending(spendingList.get(0),"abc", 1000, 23, 1, 2023, 1);
            checkSpending(spendingList.get(1), "efg", 2000, 24, 2, 2024, 2);

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
