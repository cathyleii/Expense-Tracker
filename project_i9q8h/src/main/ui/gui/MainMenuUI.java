package ui.gui;

import model.Event;
import model.EventLog;
import model.ExpensesList;
import model.Wallet;
import persistence.readers.JsonReaderExpensesList;
import persistence.readers.JsonReaderWallet;
import persistence.writers.JsonWriterListAndWallet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import java.awt.GridLayout;
import java.io.FileNotFoundException;
import java.io.IOException;


// code influenced by AlarmSystem and MenuLayoutDemo
// Represents application's main window frame
public class MainMenuUI extends Menu {
    private MenuText mainText;
    private JsonWriterListAndWallet jsonWriter;
    private JsonReaderWallet jsonReaderWallet;
    private JsonReaderExpensesList jsonReaderList;

    private static final String JSON_STORE = "./data/tracker.json";

    // Sets up JFrame for main menu
    public MainMenuUI(ExpensesList list, Wallet wallet) {
        super("Expense Tracker", list, wallet);
        this.setJMenuBar(createMenuBar());
        setVisible(true);

        initReadersAndWriter();

    }

    public void initReadersAndWriter() {
        jsonWriter = new JsonWriterListAndWallet(JSON_STORE);
        jsonReaderWallet = new JsonReaderWallet(JSON_STORE);
        jsonReaderList = new JsonReaderExpensesList(JSON_STORE);
    }

    // sets up menu bar for saving, loading, quitting, or changing name
    public JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setLayout(new GridBagLayout());
        menuBar.add(new JButton(new SaveAction()));
        menuBar.add(new JButton(new LoadAction()));
        menuBar.add(new JButton(new QuitAction()));

        menuBar.setBorder(BorderFactory.createMatteBorder(0,0,0,1,
                Color.BLACK));
        return menuBar;
    }

    private class LoadAction extends AbstractAction {

        LoadAction() {
            super("Load");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            try {
                wallet = jsonReaderWallet.read();
                expensesList = jsonReaderList.read();
                loadDialogueMessage();
                updateText();
            } catch (IOException e) {
                failureDialogueMessage();
            }
        }
    }

    public void loadDialogueMessage() {
        JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame,
                "Data was successfully loaded!",
                "Loaded Data",
                JOptionPane.DEFAULT_OPTION,
                dialogueMessageIcon("images/save_file.png"));
    }

    private class SaveAction extends AbstractAction {

        SaveAction() {
            super("Save");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            try {
                jsonWriter.open();
                jsonWriter.write(expensesList.toJson(), wallet.toJson());
                jsonWriter.close();
                saveDialogueMessage();
            } catch (FileNotFoundException e) {
                failureDialogueMessage();
                System.out.println("Unable to write to file: " + JSON_STORE);
            }
        }
    }

    public void failureDialogueMessage() {
        JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame,
                "Unable to write to file: " + JSON_STORE);
    }

    private ImageIcon dialogueMessageIcon(String fileName) {
        ImageIcon icon = new ImageIcon(fileName);
        Image scaledImage = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        return scaledIcon;
    }

    public void saveDialogueMessage() {
        JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame,
                "Data was successfully saved!",
                "Saved Data",
                JOptionPane.DEFAULT_OPTION,
                dialogueMessageIcon("images/save_file.png"));
    }

    private class QuitAction extends AbstractAction {

        QuitAction() {
            super("Quit");
        }

        // code influenced by DialogueDemo.java
        @Override
        public void actionPerformed(ActionEvent evt) {
            Object[] options = {"Cancel", "Yes"};
            JFrame frame = new JFrame();
            int n = JOptionPane.showOptionDialog(frame,
                    "Don't forget to save your data! Proceed to quit?",
                    "Save Data",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    dialogueMessageIcon("images/warning_sign.png"),
                    options,
                    options[0]);
            if (n == JOptionPane.NO_OPTION) {
                printEventLog();
                System.exit(0);
            }
        }
    }


    public String mainTextDisplay() {
        return (expensesList.getOwnerName() + "'s expense tracker!");
    }

    public void setUpMainText(JPanel buttonPanel) {
        mainText = new MenuText();
        mainText.getText().append(mainTextDisplay());
        buttonPanel.add(mainText, textConstraint);
    }

    @Override
    public void updateText() {
        String newMainText = mainTextDisplay();
        mainText.getText().setText(newMainText);
    }


    // adds button panel with buttons to manage wallet and expenses list
    @Override
    public void addButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0,1));

        setUpMainText(buttonPanel);
        buttonPanel.add(new JLabel());
        addTextLabel("Change Name Below:", buttonPanel);
        buttonPanel.add(new JButton(new ChangeNameAction(addTextField(buttonPanel))));
        buttonPanel.add(new JLabel());
        buttonPanel.add(new JButton(new ListMenuAction()));
        buttonPanel.add(new JButton(new WalletMenuAction()));

        this.add(buttonPanel);
    }

    private class ChangeNameAction extends AbstractAction {
        private JTextField newName;

        ChangeNameAction(JTextField name) {
            super("Change Name");
            newName = name;
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            if (!newName.getText().isEmpty()) {
                expensesList.setListOwnerName(newName.getText());
                wallet.setWalletOwnerName(newName.getText());
                updateText();
            }
        }
    }

    private class ListMenuAction extends AbstractAction {

        ListMenuAction() {
            super("Manage List");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            ExpensesListUI expensesListFrame = new ExpensesListUI(expensesList, wallet);
            expensesListFrame.setVisible(true);
            dispose();
        }
    }

    private class WalletMenuAction extends AbstractAction {

        WalletMenuAction() {
            super("Manage Wallet");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            WalletUI walletFrame = new WalletUI(expensesList, wallet);
            walletFrame.setVisible(true);
            dispose();
        }
    }


    public static void main(String[] args) {
        new MainMenuUI(new ExpensesList("Guest"), new Wallet("Guest"));
    }


}