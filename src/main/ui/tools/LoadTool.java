package ui.tools;

import model.Spending;
import model.SpendingList;
import persistence.JsonReader;
import ui.AccountingAppGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LoadTool extends Tool {
    private static final String JSON_STORE = "./data/spendingList.json";

    public LoadTool(AccountingAppGUI accountingAppGUI, JPanel toolArea, SpendingList spendingList) {
        super(accountingAppGUI, toolArea, spendingList);
    }

    // MODIFIES: this
    // EFFECTS:  constructs a load spending button which is then added to the JComponent (parent)
    //           which is passed in as a parameter
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Load Spending");
        addToParent(parent);
    }

    // MODIFIES: this
    // EFFECTS:  constructs a new listener object which is added to the JButton
    @Override
    protected void addListener() {
        button.addActionListener(new LoadSpendingToolClickHandler());
    }

    private class LoadSpendingToolClickHandler implements ActionListener {

        // EFFECTS: load file, set app's status to "loadded"
        @Override
        public void actionPerformed(ActionEvent event) {
            JsonReader jsonReader = new JsonReader(JSON_STORE);
            app.setStatus("welcome");
            try {
                spendingList = jsonReader.read();
                app.setSpendingList(spendingList);
                app.setStatus("loaded");
            } catch (IOException e) {
                app.failToLoadMessage();
            }
        }
    }
}
