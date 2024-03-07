package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// represent a spending list, with a name, a list of spending
public class SpendingList implements Writable {
    private ArrayList<Spending> spendingList;
    private Event event;
    private String name;

    // EFFECTS: construct spending list, create a new empty list
    public SpendingList() {
        spendingList = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: set string given as name
    public void setName(String name) {
        this.name = name;
    }

    // EFFECTS: return name of spending list
    public String getName() {
        return this.name;
    }

    // MODIFIES: this
    // EFFECTS: add spending to the end of list, set the index of spending to its current position in list
    //          starting from 1
    public void addSpending(Spending spending) {
        spendingList.add(spending);
        spending.setIndex(getSize());
        event = new Event("Spending Added");
        EventLog.getInstance().logEvent(event);
    }

    // REQUIRES: index <= size of list of spending
    // MODIFIES: this
    // EFFECTS: remove spending in list with given index, reset index of all spending in list
    public void removeSpending(int index) {
        spendingList.remove(index - 1);
        event = new Event("Spending Removed");
        EventLog.getInstance().logEvent(event);

        int resetIndex = 1;
        for (Spending spending : spendingList) {
            spending.setIndex(resetIndex);
            resetIndex++;
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", this.name);
        json.put("spendingList", spendingListToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    public JSONArray spendingListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Spending t : this.spendingList) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: return all spending in spendingList
    public ArrayList<Spending> getAllSummary() {
        event = new Event("Summary Displayed");
        EventLog.getInstance().logEvent(event);
        return this.spendingList;
    }

    // EFFECTS: return size of list of spending
    public int getSize() {
        return this.spendingList.size();
    }

    // REQUIRES: index <= spendingList.size()
    // EFFECTS: return spending according to given index
    public Spending getSpending(int index) {
        return spendingList.get(index);
    }

    // EFFECTS: return the whole spending list
    public ArrayList<Spending> getSpendingList() {
        return this.spendingList;
    }

}
