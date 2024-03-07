package ui.tools;

import model.Spending;
import model.SpendingList;
import ui.AccountingAppGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RemoveSpendingTool extends Tool {

    public RemoveSpendingTool(AccountingAppGUI accountingAppGUI, JPanel toolArea, SpendingList spendingList) {
        super(accountingAppGUI, toolArea, spendingList);
    }

    // MODIFIES: this
    // EFFECTS:  constructs an remove spending button which is then added to the JComponent (parent)
    //           which is passed in as a parameter
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Remove Spending");
        addToParent(parent);
    }

    // MODIFIES: this
    // EFFECTS:  constructs a new listener object which is added to the JButton
    @Override
    protected void addListener() {
        button.addActionListener(new RemoveSpendingToolClickHandler());
    }

    private class RemoveSpendingToolClickHandler implements ActionListener {
        private JPanel myPanel;
        private JTextField indexTextField;
        private int indexInput;

        // EFFECTS: initialize a popup window, ask for the index of the spending and remove it
        @Override
        public void actionPerformed(ActionEvent e) {
            app.setStatus("all");

            initializeWindow();
            int result = JOptionPane.showConfirmDialog(null, myPanel,
                    "Enter the Index of the Spending You want to Remove", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                indexInput = Integer.parseInt(indexTextField.getText());
            }

            if (indexInput <= spendingList.getSize()) {
                Spending spendingToRemove = spendingList.getSpending(indexInput - 1);

                JPanel myPanel = new JPanel();
                int result2 = JOptionPane.showConfirmDialog(myPanel,"The spending you are going to remove is "
                                + spendingToRemove.displaySpending(), "removed", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                if (result2 == JOptionPane.YES_OPTION) {
                    spendingList.removeSpending(indexInput);
                    app.setStatus("removed");
                }

            } else {
                JPanel myPanel = new JPanel();
                JOptionPane.showConfirmDialog(myPanel,"Invalid Input", "Invalid",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            }
        }

        private void initializeWindow() {
            myPanel = new JPanel();
            indexTextField = new JTextField(10);
            myPanel.add(new JLabel("Index:"));
            myPanel.add(indexTextField);
        }
    }
}
