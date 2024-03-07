package ui.tools;

import model.SpendingList;
import persistence.JsonWriter;
import ui.AccountingAppGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

public class SaveTool extends Tool {
    private JsonWriter jsonWriter;
    private static final String JSON_STORE = "./data/spendingList.json";

    public SaveTool(AccountingAppGUI accountingAppGUI, JPanel toolArea, SpendingList spendingList) {
        super(accountingAppGUI, toolArea, spendingList);
        jsonWriter = new JsonWriter(JSON_STORE);
    }

    // MODIFIES: this
    // EFFECTS:  constructs a save spending button which is then added to the JComponent (parent)
    //           which is passed in as a parameter
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Save File");
        addToParent(parent);
    }

    // MODIFIES: this
    // EFFECTS:  constructs a new listener object which is added to the JButton
    @Override
    protected void addListener() {
        button.addActionListener(new SaveSpendingToolClickHandler());
    }

    private class SaveSpendingToolClickHandler implements ActionListener {

        // EFFECTS: save file, set app's status to "saved"
        @Override
        public void actionPerformed(ActionEvent event) {
            try {
                jsonWriter.open();
                jsonWriter.write(spendingList);
                jsonWriter.close();
                app.setStatus("saved");
            } catch (FileNotFoundException e) {
                app.failToSaveMessage();
            }
        }
    }
}
