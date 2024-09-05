package ui.gui;

import model.EventLog;
import model.ExpensesList;
import model.Wallet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public abstract class Menu extends JFrame {
    protected static final int WIDTH = 1000;
    protected static final int HEIGHT = 800;
    protected ExpensesList expensesList;
    protected Wallet wallet;
    protected static GridBagConstraints buttonPanelConstraint;
    protected static GridBagConstraints textConstraint;


    // Sets up a menu
    public Menu(String name, ExpensesList list, Wallet wallet) {
        super(name);
        this.wallet = wallet;
        this.expensesList = list;

        buttonPanelConstraint = new GridBagConstraints();
        textConstraint = new GridBagConstraints();

        addMouseListener(new DesktopFocusAction());

        setLayout(new GridBagLayout());
        setSize(WIDTH, HEIGHT);
        addButtonPanel();

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                printEventLog();
                System.exit(0);
            }
        });
        centreOnScreen();
        setVisible(true);


    }

    public void printEventLog() {
        EventLog.getInstance().forEach(System.out::println);
    }


    // ensures the menu is the active and focused component the user interacts with
    private class DesktopFocusAction extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            Menu.this.requestFocusInWindow();
        }
    }

    // centers the window frame
    private void centreOnScreen() {
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        setLocation((width - getWidth()) / 2, (height - getHeight()) / 2);
    }

    // returns to previous menu (main menu)
    protected class ReturnPreviousMenuAction extends AbstractAction {
        private ExpensesList expensesList;
        private Wallet wallet;

        ReturnPreviousMenuAction(ExpensesList list, Wallet wallet) {
            super("Return to previous menu");
            this.expensesList = list;
            this.wallet = wallet;
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            MainMenuUI mainFrame = new MainMenuUI(expensesList, wallet);
            mainFrame.setVisible(true);
            dispose();
        }
    }

    // sets up constraints for button panel
    public void setUpButtonPanelConstraint() {
        buttonPanelConstraint.fill = GridBagConstraints.HORIZONTAL;
        buttonPanelConstraint.gridx = 0;
        buttonPanelConstraint.gridy = 3;
    }

    // sets up constraints for text
    public void setUpTextConstraint() {
        textConstraint.anchor = GridBagConstraints.PAGE_START;
        textConstraint.fill = GridBagConstraints.HORIZONTAL;
        textConstraint.gridx = 0;
        textConstraint.gridy = 2;
    }

    // sets up button panel for a menu
    public abstract void addButtonPanel();

    // updates text for any changes made by user
    public abstract void updateText();

    // adds a JTextField to the button Panel and returns it
    public JTextField addTextField(JPanel buttonPanel) {
        JTextField textField = new JTextField();

        buttonPanel.add(textField);

        return textField;
    }

    // adds a JTextArea label to the button Panel
    public void addTextLabel(String label, JPanel buttonPanel) {
        JTextArea textLabel = new JTextArea(label);
        textLabel.setEditable(false);
        buttonPanel.add(textLabel);

    }
}
