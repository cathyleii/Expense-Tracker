package persistence.readers;

import model.Wallet;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

// Represents a reader that reads wallet from JSON data stored in file
public class JsonReaderWallet extends JsonReader {

    public JsonReaderWallet(String source) {
        super(source);
    }

    // EFFECTS: reads wallet from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Wallet read() throws IOException {
        String jsonData = readFile(source);
        JSONArray jsonArray = new JSONArray(jsonData);
        return parseWallet(jsonArray);
    }


    // EFFECTS: parses wallet from JSON object
    private Wallet parseWallet(JSONArray jsonArray) {
        JSONObject walletObj = jsonArray.getJSONObject(1);
        JSONObject walletContent = walletObj.getJSONObject("wallet");
        String walletName = walletContent.getString("walletName");
        double totalAccumulated = walletContent.getDouble("totalAccumulated");
        double balance = walletContent.getDouble("balance");
        Wallet wallet = new Wallet(walletName);
        wallet.addFunds(balance);
        wallet.addTotalAccumulatedFunds(totalAccumulated);
        return wallet;
    }

}

