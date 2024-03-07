package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a spending having a category, an amount, a day of spend, a month of spend, a year of spend,
// a comment about the spending
public class Spending implements Writable {
    private final String category;
    private final double amount;
    private final int day;
    private final int month;
    private final int year;
    private int index;

    // REQUIRES: amount > 0, 1 <= day <= 31, 1 <= month <= 12,
    // MODIFIES: this
    // EFFECTS: constructs spending, set category to category given, set amount to amount given, set day to day given,
    //          set comment to comment given
    public Spending(String category, double amount, int day, int month, int year) {
        this.category = category;
        this.amount = amount;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    // REQUIRES: givenIndex > 0
    // MODIFIES: this
    // EFFECTS: set index of spending to given index
    public void setIndex(int givenIndex) {
        this.index = givenIndex;
    }

    // EFFECTS: display spending in human language
    public String displaySpending() {
        return (index + ". " + year + "/" + month + "/"
                + day + " " + category + " $" + amount);
    }

    public String getCategory() {
        return this.category;
    }

    public double getAmount() {
        return this.amount;
    }

    public int getDay() {
        return this.day;
    }

    public int getMonth() {
        return this.month;
    }

    public  int getYear() {
        return this.year;
    }

    public int getIndex() {
        return this.index;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("category", this.category);
        json.put("amount", this.amount);
        json.put("day", this.day);
        json.put("month", this.month);
        json.put("year", this.year);
        json.put("index", this.index);
        return json;
    }
}
