package ui.tools;

import model.Spending;
import model.SpendingList;
import ui.AccountingAppGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddSpendingTool extends Tool {

    public AddSpendingTool(AccountingAppGUI accountingAppGUI, JComponent parent, SpendingList spendingList) {
        super(accountingAppGUI, parent, spendingList);
    }

    // MODIFIES: this
    // EFFECTS:  constructs an add spending button which is then added to the JComponent (parent)
    //           which is passed in as a parameter
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Add Spending");
        addToParent(parent);
    }

    // MODIFIES: this
    // EFFECTS:  constructs a new listener object which is added to the JButton
    @Override
    protected void addListener() {
        button.addActionListener(new AddSpendingToolClickHandler());
    }

    private class AddSpendingToolClickHandler implements ActionListener {
        private JTextField category;
        private JTextField year;
        private JTextField month;
        private JTextField day;
        private JTextField amount;
        private JPanel myPanel;

        // EFFECTS: initialize a popup window, record user's input and add spending to spendingList
        @Override
        public void actionPerformed(ActionEvent e) {
            app.setStatus("welcome");

            initializeFields();
            setupWindow(myPanel, year, month, day, amount, category);

            int result = JOptionPane.showConfirmDialog(null, myPanel,
                    "Please Enter Information about your spending", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String categoryInput = category.getText();
                int yearInput = Integer.parseInt(year.getText());
                int monthInput = Integer.parseInt(month.getText());
                int dayInput = Integer.parseInt(year.getText());
                Double amountInput = Double.parseDouble(amount.getText());

                Spending spending = new Spending(categoryInput, amountInput, dayInput, monthInput, yearInput);
                spendingList.addSpending(spending);
                app.setStatus("added");

                myPanel = new JPanel();
                JOptionPane.showConfirmDialog(myPanel,"The spending " + spending.displaySpending()
                                + " has been added to list", "Added spending", JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
            }

        }

        // EFFECTS: helper function of actionPerformed, initialize fields needed
        private void initializeFields() {
            category = new JTextField(10);
            year = new JTextField(10);
            month = new JTextField(10);
            day = new JTextField(10);
            amount = new JTextField(10);
            myPanel = new JPanel();
        }

        // EFFECTS: helper function for actionPerformed
        private void setupWindow(JPanel myPanel, JTextField year, JTextField month, JTextField day, JTextField amount,
                                 JTextField category) {
            myPanel.add(new JLabel("Category"));
            myPanel.add(category);
            myPanel.add(Box.createHorizontalStrut(15)); // a spacer
            myPanel.add(new JLabel("Year"));
            myPanel.add(year);
            myPanel.add(Box.createHorizontalStrut(15)); // a spacer
            myPanel.add(new JLabel("Month"));
            myPanel.add(month);
            myPanel.add(Box.createHorizontalStrut(15)); // a spacer
            myPanel.add(new JLabel("Day"));
            myPanel.add(day);
            myPanel.add(Box.createHorizontalStrut(15)); // a spacer
            myPanel.add(new JLabel("Amount"));
            myPanel.add(amount);
        }
    }
}
