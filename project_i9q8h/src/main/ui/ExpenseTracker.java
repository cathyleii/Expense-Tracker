package ui;

import model.Expense;
import model.ExpensesList;
import model.Wallet;
import persistence.readers.JsonReaderExpensesList;
import persistence.readers.JsonReaderWallet;
import persistence.writers.JsonWriterListAndWallet;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// code influenced by TellerApp
// Expense tracker application
public class ExpenseTracker {
    private ExpensesList expensesList;
    private Wallet wallet;
    private Scanner input;
    private JsonWriterListAndWallet jsonWriter;
    private JsonReaderWallet jsonReaderWallet;
    private JsonReaderExpensesList jsonReaderList;

    private static final String JSON_STORE = "./data/tracker.json";
    private static final String CHANGE_OWNER_NAME_COMMAND = "change name";
    private static final String ADD_EXPENSE_COMMAND = "add";
    private static final String REMOVE_EXPENSE_COMMAND = "remove";
    private static final String SAVE_COMMAND = "save";
    private static final String LOAD_COMMAND = "load";
    private static final String QUIT_COMMAND = "quit";
    private static final String VIEW_EXPENSES_LIST_MENU_COMMAND = "list menu";
    private static final String VIEW_EXPENSES_LIST_COMMAND = "view list";
    private static final String VIEW_WALLET_MENU_COMMAND = "wallet menu";
    private static final String VIEW_WALLET_BALANCE = "view balance";
    private static final String ADD_FUNDS_COMMAND = "add funds";
    private static final String REMOVE_FUNDS_COMMAND = "remove funds";
    private static final String RETURN_COMMAND = "return";


    // EFFECTS: runs the expense tracker application
    public ExpenseTracker() {
        input = new Scanner(System.in);

        runTracker();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runTracker() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals(QUIT_COMMAND)) {
                keepGoing = false;
            } else {
                processMainMenuCommand(command);
            }
        }

        System.out.println("\nQuitting the application...");
    }

    // MODIFIES: this
    // EFFECTS: processes user command in the main menu
    private void processMainMenuCommand(String command) {
        switch (command) {
            case CHANGE_OWNER_NAME_COMMAND:
                doOwnerNameChange();
                break;
            case VIEW_EXPENSES_LIST_MENU_COMMAND:
                displayExpensesListMenu();
                break;
            case VIEW_WALLET_MENU_COMMAND:
                displayWalletMenu();
                break;
            case SAVE_COMMAND:
                saveListAndWallet();
                break;
            case LOAD_COMMAND:
                loadList();
                loadWallet();
                break;
            default:
                printUnexpectedOption();
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command in the expenses list display menu
    private void processExpensesListCommand(String command) {
        switch (command) {
            case VIEW_EXPENSES_LIST_COMMAND:
                doExpensesListDisplay();
                break;
            case ADD_EXPENSE_COMMAND:
                doExpenseAddition();
                break;
            case REMOVE_EXPENSE_COMMAND:
                doExpenseRemoval();
                break;
            case RETURN_COMMAND:
                displayMenu();
                break;
            default:
                printUnexpectedOption();
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command in the wallet display menu
    private void processWalletCommand(String command) {
        switch (command) {
            case ADD_FUNDS_COMMAND:
                doWalletFundsAddition();
                break;
            case REMOVE_FUNDS_COMMAND:
                doWalletFundsRemoval();
                break;
            case VIEW_WALLET_BALANCE:
                doWalletBalanceDisplay();
                break;
            case RETURN_COMMAND:
                displayMenu();
                break;
            default:
                printUnexpectedOption();
                break;
        }
    }

    // EFFECTS: displays the wallet balance related information,
    // including current wallet balance and total money accumulated
    private void doWalletBalanceDisplay() {
        boolean stayOnBalanceDisplay = true;
        while (stayOnBalanceDisplay) {
            System.out.println(wallet.getOwnerName() + "'s total money accumulation: $" + wallet.getTotalAccumulated());
            System.out.println(wallet.getOwnerName() + "'s current balance: $" + wallet.getBalance());
            printReturn("wallet menu");

            if (readInput().equals(RETURN_COMMAND)) {
                stayOnBalanceDisplay = false;
                displayWalletMenu();
            } else {
                System.out.println("Please press '" + RETURN_COMMAND + "' to return to the previous menu");
            }
        }
    }


    // MODIFIES: this
    // EFFECTS: initializes expenses list, wallet, and scanner
    private void init() {
        expensesList = new ExpensesList("Guest");
        wallet = new Wallet("Guest");
        jsonWriter = new JsonWriterListAndWallet(JSON_STORE);
        jsonReaderList = new JsonReaderExpensesList(JSON_STORE);
        jsonReaderWallet = new JsonReaderWallet(JSON_STORE);
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nWelcome to your personal expense tracker!");
        System.out.println("\nPlease enter:");
        System.out.println("\t=> '" + CHANGE_OWNER_NAME_COMMAND + "' to set/change your name.");
        System.out.println("\t=> '" + VIEW_EXPENSES_LIST_MENU_COMMAND + "' to view the expenses list menu.");
        System.out.println("\t=> '" + VIEW_WALLET_MENU_COMMAND + "' to view the wallet menu.");
        System.out.println("\t=> '" + SAVE_COMMAND + "' to save your current list and wallet.");
        System.out.println("\t=> '" + LOAD_COMMAND + "' to load your previously saved list and wallet.");
        System.out.println("\t=> '" + QUIT_COMMAND + "' to quit the application.");
    }

    // EFFECTS: displays menu of expenses list related options to the user
    private void displayExpensesListMenu() {
        System.out.println("\nPlease choose from the following options to adjust your expenses list:");
        System.out.println("\t=> '" + VIEW_EXPENSES_LIST_COMMAND + "' to view your expenses list.");
        System.out.println("\t=> '" + ADD_EXPENSE_COMMAND + "' to add an expense to your list.");
        System.out.println("\t=> '" + REMOVE_EXPENSE_COMMAND + "' to remove an expense from your list.");
        printReturn("main menu");

        processExpensesListCommand(readInput());
    }


    // EFFECTS: displays menu of wallet related options to the user
    private void displayWalletMenu() {
        System.out.println("\nPlease choose from the following options to adjust your wallet:");
        System.out.println("\t=> '" + VIEW_WALLET_BALANCE + "' to view your wallet's balance");
        System.out.println("\t=> '" + ADD_FUNDS_COMMAND + "' to add funds to your wallet");
        System.out.println("\t=> '" + REMOVE_FUNDS_COMMAND + "' to remove funds from your wallet");
        printReturn("main menu");

        processWalletCommand(readInput());
    }

    // EFFECTS: reads the string input and converts it to lower case
    private String readInput() {
        String command = input.next();
        command = command.toLowerCase();
        return command;
    }

    // MODIFIES: this
    // EFFECTS: conducts a change of the owner's name (for the list and the wallet)
    private void doOwnerNameChange() {
        System.out.println("Please enter your name:");
        String name = input.next();
        expensesList.setListOwnerName(name);
        wallet.setWalletOwnerName(name);
        System.out.println("Name has been set to '" + name + "'!");
    }

    // MODIFIES: this
    // EFFECTS: conducts an addition of an expense to the expenses list
    private void doExpenseAddition() {
        System.out.println("Please enter the name of the expense you wish to add:");
        String name = input.next();
        name = name.toLowerCase();
        System.out.println("Please enter the cost of the expense:");
        double cost = input.nextDouble();

        if (cost >= 0.0) {
            Expense expense = new Expense(name, cost);
            expensesList.addExpense(expense, wallet);
            System.out.println("Added '" + name + "' costing $" + cost + " to the tracker!");
        } else {
            System.out.println("Cannot add an expense with a negative price.");
        }
    }

    // MODIFIES: this
    // EFFECTS: conducts a removal of an expense from the expenses list
    private void doExpenseRemoval() {
        System.out.println("Please enter the name of the expense you would like to remove:");
        String name = input.next();
        name = name.toLowerCase();
        System.out.println("Please enter the price of the expense you would like to remove:");
        double price = input.nextDouble();
        if (expensesList.listContainsExpense(name, price)) {
            for (Expense exp : expensesList.getList()) {
                if (exp.getExpenseName().equals(name) && exp.getExpensePrice() == price) {
                    expensesList.removeExpense(exp, wallet);
                }
            }
            System.out.println("Removed '" + name + "' costing $" + price + " from the tracker!");
        } else {
            System.out.println("Your list does not contain this expense.");
        }
    }

    // EFFECTS: displays the current list of expenses
    private void doExpensesListDisplay() {
        boolean stayOnListDisplay = true;
        while (stayOnListDisplay) {
            System.out.println("Here is " + expensesList.getOwnerName() + "'s current list of expenses:");
            for (Expense exp : expensesList.getList()) {
                System.out.println("\t=> '" + exp.getExpenseName() + "', $" + exp.getExpensePrice());
            }
            double totalCost = expensesList.calculateTotalCost();
            System.out.println("Total cost: $" + totalCost);
            printReturn("expenses list menu");

            if (readInput().equals(RETURN_COMMAND)) {
                stayOnListDisplay = false;
                displayExpensesListMenu();
            } else {
                System.out.println("Please press '" + RETURN_COMMAND + "' to return to the previous menu");
            }
        }
    }


    // EFFECTS: conducts an addition of funds to the wallet
    private void doWalletFundsAddition() {
        System.out.println("Please enter an amount to add to your wallet:");
        double amount = input.nextDouble();
        if (amount >= 0) {
            wallet.addFunds(amount);
            wallet.addTotalAccumulatedFunds(amount);
            System.out.println("Added $" + amount + " to wallet balance!");
            System.out.println("\n" + wallet.getOwnerName() + "'s balance is now $" + wallet.getBalance());
        } else {
            System.out.println("Cannot add negative funds to a wallet's balance.");
        }
    }

    // EFFECTS: conducts a subtraction of funds to the wallet
    private void doWalletFundsRemoval() {
        System.out.println("Please enter an amount to add to your wallet:");
        double amount = input.nextDouble();
        if (amount >= 0 && amount <= wallet.getBalance()) {
            wallet.removeFunds(amount);
            System.out.println("Subtracted $" + amount + " from wallet balance!");
            System.out.println("\n" + wallet.getOwnerName() + "'s balance is now " + wallet.getBalance());
        } else if (amount > wallet.getBalance()) {
            System.out.println("Wallet has insufficient funds.");
        } else {
            System.out.println("Cannot subtract negative funds from a wallet's balance.");
        }
    }

    // EFFECTS: print statement for when an input not from the list of options is given
    private void printUnexpectedOption() {
        System.out.println("\nPlease choose from the options listed.");
    }

    // EFFECTS: print statement to return to previous menu, specified/given by menu
    private void printReturn(String menu) {
        System.out.println("\nEnter '" + RETURN_COMMAND + "' to return to the " + menu);
    }

    // code influenced by JSONSerializationDemo
    // EFFECTS: saves the expenses list and wallet to file
    private void saveListAndWallet() {
        try {
            jsonWriter.open();
            jsonWriter.write(expensesList.toJson(), wallet.toJson());
            jsonWriter.close();
            System.out.println("Saved " + expensesList.getOwnerName() + "'s list and wallet to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }


    // MODIFIES: this
    // EFFECTS: loads wallet from file
    private void loadWallet() {
        try {
            wallet = jsonReaderWallet.read();
            System.out.println("Loaded " + wallet.getOwnerName() + "'s wallet from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads expenses list from file
    private void loadList() {
        try {
            expensesList = jsonReaderList.read();
            System.out.println("Loaded " + expensesList.getOwnerName() + "'s expenses list from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
