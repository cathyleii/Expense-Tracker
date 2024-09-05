package model;

import org.json.JSONObject;
import org.json.JSONArray;
import persistence.Writable;

import java.util.LinkedList;
import java.util.List;

// Represents a list of expenses with the owner's name
public class ExpensesList implements Writable {
    private final List<Expense> list;
    private String name;
    private double totalCost;

    // REQUIRES: ownerName must have a non-zero length
    // EFFECTS: creates an empty list of expenses with the owner's name and 0 total cost
    public ExpensesList(String ownerName) {
        list = new LinkedList<>();
        name = ownerName;
        totalCost = 0.00;
    }

    // EFFECTS: returns true if an expense with the given name and price is in the list, false otherwise
    public boolean listContainsExpense(String name, double price) {
        for (Expense exp : list) {
            if (exp.getExpenseName().equals(name) && exp.getExpensePrice() == price) {
                return true;
            }
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: adds the expense to the list of expenses and subtracts its cost from the wallet balance
    public void addExpense(Expense exp, Wallet wallet) {
        list.add(exp);
        wallet.removeFunds(exp.getExpensePrice());
        EventLog.getInstance().logEvent(new Event("'" + exp.getExpenseName() + "'" + " added to list."));
    }

    // REQUIRES: expense must be in expenses list
    // MODIFIES: this
    // EFFECTS: removes the expense from this list of expenses if it's in the list and adds its price to
    // the funds of the wallet (refunds it)
    public void removeExpense(Expense exp, Wallet wallet) {
        list.remove(exp);
        wallet.refundExpense(exp);
        EventLog.getInstance().logEvent(new Event("'" + exp.getExpenseName() + "'" + "removed from list."));
    }

    // MODIFIES: this
    // EFFECTS: sets owner name to given name
    public void setListOwnerName(String ownerName) {
        name = ownerName;
        EventLog.getInstance().logEvent(new Event("Expenses list owner name changed to: " + ownerName));
    }

    // MODIFIES: this
    // EFFECTS: sums up all the prices from each expense on the list
    public double calculateTotalCost() {
        double currentTotalCost = totalCost;
        if (!list.isEmpty()) {
            for (Expense exp : list) {
                currentTotalCost += exp.getExpensePrice();
            }
        }
        return currentTotalCost;
    }

    // REQUIRES: total >= 0
    // MODIFIES: this
    // EFFECTS: sets total cost to given total
    public void setTotalCost(double total) {
        totalCost = total;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("list", expensesToJson());
        json.put("name", name);
        return json;
    }

    // EFFECTS: returns expenses in the list as a JSON array
    private JSONArray expensesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Expense e : list) {
            jsonArray.put(e.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns the total cost calculated from the list of expenses
    public double getTotalCost() {
        return totalCost;
    }

    // EFFECTS: returns the name of the expenses list's owner
    public String getOwnerName() {
        return name;
    }

    // EFFECTS: returns the list containing all the expenses
    public List<Expense> getList() {
        return list;
    }


}
