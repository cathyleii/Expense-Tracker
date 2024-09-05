package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

// Represents a single expense with a name and its price
public class Expense implements Writable {
    private String name;
    private double price;

    // REQUIRES: expenseName must have a non-zero length, price > 0,
    // EFFECTS: constructs an expense with a name and price
    public Expense(String expenseName, Double price) {
        name = expenseName;
        this.price = price;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("price", price);
        return json;
    }

    // REQUIRES: price > 0
    // EFFECTS: sets the price of expense
    public void setExpensePrice(double price) {
        this.price = price;
    }

    // REQUIRES: expenseName must have a non-zero length
    // EFFECTS: sets the name of the expense
    public void setExpenseName(String expenseName) {
        name = expenseName;
    }

    // EFFECTS: returns the expense's name
    public String getExpenseName() {
        return name;
    }

    // EFFECTS: returns the expense's price
    public double getExpensePrice() {
        return price;
    }
}
