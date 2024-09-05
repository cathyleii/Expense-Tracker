package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a wallet with a balance, total money accumulated, and the owner's name
public class Wallet implements Writable {
    private String name;
    private double totalAccumulated;
    private double balance;

    // REQUIRES: ownerName must have a non-zero length
    // EFFECTS: constructs a wallet with the owner's name, 0 balance, and 0 total money accumulated
    public Wallet(String ownerName) {
        name = ownerName;
        totalAccumulated = 0.00;
        balance = 0.00;
    }

    // MODIFIES: this
    // EFFECTS: sets owner name of wallet to given name
    public void setWalletOwnerName(String ownerName) {
        name = ownerName;
        EventLog.getInstance().logEvent(new Event("Wallet owner name changed to: " + ownerName));
    }

    // MODIFIES: this
    // EFFECTS: adds given amount of funds to balance
    public void addFunds(double funds) {
        balance += funds;
        EventLog.getInstance().logEvent(new Event(funds + "$ added to wallet balance."));
    }

    // MODIFIES: this
    // EFFECTS: adds given amount of funds to total accumulated funds
    public void addTotalAccumulatedFunds(double funds) {
        totalAccumulated += funds;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("walletName", name);
        json.put("totalAccumulated", totalAccumulated);
        json.put("balance", balance);
        return json;
    }

    // MODIFIES: this
    // EFFECTS: refunds an expense by adding the expense's price back to the balance
    public void refundExpense(Expense exp) {
        balance += exp.getExpensePrice();
    }

    // REQUIRES: balance >= funds
    // MODIFIES: this
    // EFFECTS: removes given amount of funds to balance
    public void removeFunds(double funds) {
        balance -= funds;
        EventLog.getInstance().logEvent(new Event(funds + "$ removed from wallet balance."));
    }

    // EFFECTS: returns the wallet owner's name
    public String getOwnerName() {
        return name;
    }

    // EFFECTS: returns the wallet's balance
    public double getBalance() {
        return balance;
    }

    // EFFECTS: returns the wallet's total money accumulated
    public double getTotalAccumulated() {
        return totalAccumulated;
    }
}
