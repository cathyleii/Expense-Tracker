package persistence.writers;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// code influenced by JsonSerializationDemo
public class JsonWriterListAndWallet {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination fil
    public JsonWriterListAndWallet(String destination) {
        this.destination = destination;
    }


    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }


    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    protected void saveToFile(String json) {
        writer.print(json);
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of expenses list and wallet as JSON array to file
    public void write(JSONObject el, JSONObject wallet) {
        JSONObject walletObj = new JSONObject();
        walletObj.put("wallet", wallet);
        JSONObject elObj = new JSONObject();
        elObj.put("expensesList", el);

        JSONArray array = new JSONArray();
        array.put(elObj);
        array.put(walletObj);

        saveToFile(array.toString(TAB));
    }
}
