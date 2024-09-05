package ui.gui;

import model.Expense;
import model.ExpensesList;
import model.Wallet;
import ui.gui.ExpensesListText;
import ui.gui.Menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

// code influenced by TextDemo
public class ExpensesListUI extends Menu {
    private ExpensesListText expensesListText;

    // Sets up JFrame for Expenses List menu
    public ExpensesListUI(ExpensesList list, Wallet wallet) {
        super("List Menu", list, wallet);
        addExpensesListText();
    }

    @Override
    public void addButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 3));

        addAddExpenseButton(buttonPanel);
        addRemoveExpenseButton(buttonPanel);

        addTextLabel("Name", buttonPanel);
        addTextLabel("Price", buttonPanel);

        buttonPanel.add(new JButton(new ReturnPreviousMenuAction(expensesList, wallet)));

        setUpButtonPanelConstraint();

        this.add(buttonPanel, buttonPanelConstraint);
    }


    public void addAddExpenseButton(JPanel buttonPanel) {
        buttonPanel.add(new JButton(new AddExpenseAction(addTextField(buttonPanel), addTextField(buttonPanel))));

    }

    public void addRemoveExpenseButton(JPanel buttonPanel) {
        buttonPanel.add(new JButton(new RemoveExpenseAction(
                addTextField(buttonPanel), addTextField(buttonPanel), expensesList, wallet)));

    }


    public void addExpensesListText() {
        expensesListText = new ExpensesListText(expensesList);

        setUpTextConstraint();

        this.add(expensesListText, textConstraint);


    }

    private class RemoveExpenseAction extends AbstractAction {
        private JTextField expenseNameTextField;
        private JTextField expensePriceTextField;
        private ExpensesList expensesList;
        private Wallet wallet;

        RemoveExpenseAction(JTextField name, JTextField price, ExpensesList list, Wallet wallet) {
            super("Remove Expense");
            expenseNameTextField = name;
            expensePriceTextField = price;
            expensesList = list;
            this.wallet = wallet;
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            String name = expenseNameTextField.getText();
            double price = Double.parseDouble(expensePriceTextField.getText());
            Expense toRemove = null;

            for (Expense expense : expensesList.getList()) {
                if (name.equals(expense.getExpenseName()) && (price == expense.getExpensePrice())) {
                    toRemove = expense;
                }
            }
            if (toRemove != null) {
                expensesList.removeExpense(toRemove, wallet);
            }

            updateText();

        }
    }

    private class AddExpenseAction extends AbstractAction {
        private JTextField expenseNameTextField;
        private JTextField expensePriceTextField;

        AddExpenseAction(JTextField name, JTextField price) {
            super("Add Expense");
            expenseNameTextField = name;
            expensePriceTextField = price;
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            String name = expenseNameTextField.getText();
            double price = Double.parseDouble(expensePriceTextField.getText());

            Expense expense = new Expense(name, price);
            expensesList.addExpense(expense, wallet);

            updateText();

        }
    }

    @Override
    public void updateText() {
        String newListText = expensesListText.expensesDisplay();
        expensesListText.getText().setText(newListText);
    }


}
