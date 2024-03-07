package ui.tools;

import model.SpendingList;
import ui.AccountingAppGUI;

import javax.swing.*;

public abstract class Tool {

    protected JButton button;
    protected AccountingAppGUI app;
    protected SpendingList spendingList;

    // construct Tool, set app to given, spendingList to given, call creatButton, addToParent and addListener methods
    public Tool(AccountingAppGUI app, JComponent parent, SpendingList spendingList) {
        this.app = app;
        this.spendingList = spendingList;
        createButton(parent);
        addToParent(parent);
        addListener();
    }

    // MODIFIES: parent
    // EFFECTS: adds the given button to the parent component
    protected  void addToParent(JComponent parent) {
        parent.add(button);
    }

    // EFFECTS: creates button to activate tool
    protected abstract void createButton(JComponent parent);

    // MODIFIES: this
    // EFFECTS:  customizes the button used for this tool
    protected JButton customizeButton(JButton button) {
        button.setBorderPainted(true);
        button.setFocusPainted(true);
        button.setContentAreaFilled(true);
        return button;
    }

    // EFFECTS: adds a listener for this tool
    protected abstract void addListener();
}
