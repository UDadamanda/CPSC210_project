package persistence;

import model.Spending;
import model.SpendingList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class JsonReader {
    private final String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads spendingList from file and returns it;
    // throws IOException if an error occurs reading data from file
    public SpendingList read() throws IOException {
        String jsonData = readFile(this.source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseSpendingList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses spendingList from JSON object and returns it
    private SpendingList parseSpendingList(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        SpendingList spendingList = new SpendingList();
        spendingList.setName(name);
        addSpendingList(spendingList, jsonObject);
        return spendingList;
    }

    // MODIFIES: this
    // EFFECTS: parses spendingList from JSON object and adds them to spendingList
    private void addSpendingList(SpendingList spendingList, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("spendingList");
        for (Object json : jsonArray) {
            JSONObject nextSpending = (JSONObject) json;
            addSpending(spendingList, nextSpending);
        }
    }

    // MODIFIES: spendingList
    // EFFECTS: parses spending from JSON object and adds it to spendingList
    private void addSpending(SpendingList spendingList, JSONObject jsonObject) {
        String category = jsonObject.getString("category");
        double amount = jsonObject.getDouble("amount");
        int day = jsonObject.getInt("day");
        int month = jsonObject.getInt("month");
        int year = jsonObject.getInt("year");
        Spending spending = new Spending(category, amount, day, month, year);
        spendingList.addSpending(spending);
    }




}
