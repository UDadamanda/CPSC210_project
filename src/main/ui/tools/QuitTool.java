package ui.tools;

import model.Event;
import model.EventLog;
import model.SpendingList;
import persistence.JsonWriter;
import ui.AccountingAppGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

public class QuitTool extends Tool {
    private JsonWriter jsonWriter;
    private static final String JSON_STORE = "./data/spendingList.json";

    public QuitTool(AccountingAppGUI accountingAppGUI, JPanel toolArea, SpendingList spendingList) {
        super(accountingAppGUI, toolArea, spendingList);
    }

    // MODIFIES: this
    // EFFECTS:  constructs a quit button which is then added to the JComponent (parent)
    //           which is passed in as a parameter
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Quit");
        addToParent(parent);
    }

    // MODIFIES: this
    // EFFECTS:  constructs a new listener object which is added to the JButton
    @Override
    protected void addListener() {
        button.addActionListener(new QuitToolClickHandler());
    }

    private class QuitToolClickHandler implements ActionListener {

        // EFFECTS: ask if the user want to save file, if yeas save file and end program, otherwise end program only
        @Override
        public void actionPerformed(ActionEvent event) {
            JPanel myPanel = new JPanel();
            app.setStatus("welcome");
            int result = JOptionPane.showConfirmDialog(myPanel,"Do you want to save your work before exist?",
                    "Save", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (result == JOptionPane.YES_OPTION) {
                jsonWriter = new JsonWriter(JSON_STORE);
                try {
                    writeFile();
                    myPanel = new JPanel();
                    JOptionPane.showConfirmDialog(myPanel,"File has been saved to " + JSON_STORE,
                            "Saved", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE);

                    eventLogPrinter();
                    System.exit(0);
                } catch (FileNotFoundException e) {
                    app.failToSaveMessage();
                }
            } else if (result == JOptionPane.NO_OPTION) {
                eventLogPrinter();

                System.exit(0);
            }
        }

        // EFFECTS: helper function for actionPerformed
        private void eventLogPrinter() {
            EventLog eventLog = EventLog.getInstance();
            for (Event logEvent : eventLog) {
                System.out.println(logEvent.toString());
            }
        }

        // MODIFIES: this
        // EFFECTS: helper function for actionPerformed
        private void writeFile() throws FileNotFoundException {
            jsonWriter.open();
            jsonWriter.write(spendingList);
            jsonWriter.close();
        }
    }
}
