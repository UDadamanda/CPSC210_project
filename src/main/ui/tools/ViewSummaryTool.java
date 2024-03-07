package ui.tools;

import model.SpendingList;
import ui.AccountingAppGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// construct
public class ViewSummaryTool extends Tool {
    public ViewSummaryTool(AccountingAppGUI accountingAppGUI, JPanel toolArea, SpendingList spendingList) {
        super(accountingAppGUI, toolArea, spendingList);
    }

    // MODIFIES: this
    // EFFECTS:  constructs a view summary button which is then added to the JComponent (parent)
    //           which is passed in as a parameter
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("View Summary");
        addToParent(parent);
    }

    // MODIFIES: this
    // EFFECTS:  constructs a new listener object which is added to the JButton
    @Override
    protected void addListener() {
        button.addActionListener(new ViewSummaryToolClickHandler());
    }

    private class ViewSummaryToolClickHandler implements ActionListener {

        // EFFECTS: set app's status to all
        @Override
        public void actionPerformed(ActionEvent e) {
            app.setStatus("all");
        }
    }
}
