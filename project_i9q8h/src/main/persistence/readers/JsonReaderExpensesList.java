package persistence.readers;

import java.io.IOException;

import model.Expense;
import model.ExpensesList;
import org.json.*;

// Represents a reader that reads expenses list from JSON data stored in file
public class JsonReaderExpensesList extends JsonReader {

    public JsonReaderExpensesList(String source) {
        super(source);
    }

    // EFFECTS: reads expenses list from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ExpensesList read() throws IOException {
        String jsonData = readFile(source);
        JSONArray jsonArray = new JSONArray(jsonData);
        return parseExpensesList(jsonArray);
    }


    // EFFECTS: parses expenses list from JSON object and returns it
    private ExpensesList parseExpensesList(JSONArray jsonArray) throws IOException {
        JSONObject listObj = jsonArray.getJSONObject(0);
        JSONObject jsonList = listObj.getJSONObject("expensesList");
        String listName = jsonList.getString("name");
        ExpensesList el = new ExpensesList(listName);
        addExpenses(el, jsonList);
        return el;
    }

    // MODIFIES: el
    // EFFECTS: parses list from JSON object and adds them to expenses list
    private void addExpenses(ExpensesList el, JSONObject jsonList) throws IOException {
        JSONArray jsonArray = jsonList.getJSONArray("list");
        for (Object json : jsonArray) {
            JSONObject nextExpense = (JSONObject) json;
            addExpense(el, nextExpense);
        }
    }

    // MODIFIES: el
    // EFFECTS: parses expense from JSON object and adds it to expenses list
    private void addExpense(ExpensesList el, JSONObject jsonObject) throws IOException {
        String name = jsonObject.getString("name");
        double price = jsonObject.getDouble("price");
        Expense expense = new Expense(name, price);
        JsonReaderWallet wallet = new JsonReaderWallet(source);
        el.addExpense(expense, wallet.read());
    }

}


