package ui.gui;

import model.ExpensesList;
import model.Wallet;
import ui.gui.Menu;
import ui.gui.WalletText;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class WalletUI extends Menu {
    private WalletText walletText;

    // Sets up JFrame for wallet menu
    public WalletUI(ExpensesList list, Wallet wallet) {
        super("Wallet Menu", list, wallet);
        addWalletText();
    }

    @Override
    public void addButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 2));

        addAddFundsButton(buttonPanel);
        addRemoveFundsButton(buttonPanel);

        addTextLabel("Amount", buttonPanel);
        buttonPanel.add(new JButton(new ReturnPreviousMenuAction(expensesList, wallet)));

        setUpButtonPanelConstraint();

        this.add(buttonPanel, buttonPanelConstraint);
    }


    public void addWalletText() {
        walletText = new WalletText(wallet);

        setUpTextConstraint();

        this.add(walletText, textConstraint);
    }

    public void addRemoveFundsButton(JPanel buttonPanel) {
        buttonPanel.add(new JButton(new RemoveFundsAction(addTextField(buttonPanel))));
    }

    private class RemoveFundsAction extends AbstractAction {
        private JTextField amountToRemove;

        RemoveFundsAction(JTextField amount) {
            super("Remove Funds");
            amountToRemove = amount;
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            double funds = Double.parseDouble(amountToRemove.getText());
            wallet.removeFunds(funds);

            updateText();
        }
    }

    public void addAddFundsButton(JPanel buttonPanel) {
        buttonPanel.add(new JButton(new AddFundsAction(addTextField(buttonPanel))));
    }

    private class AddFundsAction extends AbstractAction {
        private JTextField amountToAdd;

        AddFundsAction(JTextField amount) {
            super("Add Funds");
            amountToAdd = amount;
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            double funds = Double.parseDouble(amountToAdd.getText());
            wallet.addFunds(funds);
            wallet.addTotalAccumulatedFunds(funds);

            updateText();

        }

    }

    @Override
    public void updateText() {
        String newWalletText = walletText.walletDisplay();
        walletText.getText().setText(newWalletText);
    }

}
