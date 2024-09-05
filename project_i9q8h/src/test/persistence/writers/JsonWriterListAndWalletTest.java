package persistence.writers;

import model.Expense;
import model.ExpensesList;
import model.Wallet;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import persistence.JsonTest;
import persistence.readers.JsonReaderExpensesList;
import persistence.readers.JsonReaderWallet;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterListAndWalletTest extends JsonTest {

    @Test
    void testWriterListInvalidFile() {
        try {
            Wallet wallet = new Wallet("Guest");
            ExpensesList el = new ExpensesList("Guest");
            JSONObject jsonWallet = wallet.toJson();
            JSONObject jsonList = el.toJson();
            JsonWriterListAndWallet writer = new JsonWriterListAndWallet("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }



    @Test
    void testWriterEmptyListNoBalanceNothingAccumulated() {
        try {
            ExpensesList el = new ExpensesList("Joe");
            Wallet wallet = new Wallet("Joe");
            JSONObject jsonWallet = wallet.toJson();
            JSONObject jsonList = el.toJson();

            JsonWriterListAndWallet writer = new JsonWriterListAndWallet("./data/testWriterEmptyListNoBalanceNothingAccumulated.json");
            writer.open();
            writer.write(jsonList, jsonWallet);
            writer.close();

            JsonReaderExpensesList listReader = new JsonReaderExpensesList("./data/testWriterEmptyListNoBalanceNothingAccumulated.json");
            el = listReader.read();
            checkList("Joe", 0, el);

            JsonReaderWallet walletReader = new JsonReaderWallet("./data/testWriterEmptyListNoBalanceNothingAccumulated.json");
            wallet = walletReader.read();
            checkWallet("Joe", 0, 0, wallet);

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterEmptyListNoBalanceSomeAccumulated() {
        try {
            ExpensesList el = new ExpensesList("Joe");
            Wallet wallet = new Wallet("Joe");
            wallet.addTotalAccumulatedFunds(100);
            JSONObject jsonWallet = wallet.toJson();
            JSONObject jsonList = el.toJson();

            JsonWriterListAndWallet writer = new JsonWriterListAndWallet("./data/testWriterEmptyListNoBalanceSomeAccumulated.json");
            writer.open();
            writer.write(jsonList, jsonWallet);
            writer.close();

            JsonReaderExpensesList listReader = new JsonReaderExpensesList("./data/testWriterEmptyListNoBalanceSomeAccumulated.json");
            el = listReader.read();
            checkList("Joe", 0, el);

            JsonReaderWallet walletReader = new JsonReaderWallet("./data/testWriterEmptyListNoBalanceSomeAccumulated.json");
            wallet = walletReader.read();
            checkWallet("Joe", 100.0,0.0, wallet);

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterEmptyListSomeBalanceSomeAccumulated() {
        try {
            ExpensesList el = new ExpensesList("Joe");
            Wallet wallet = new Wallet("Joe");
            wallet.addFunds(50);
            wallet.addTotalAccumulatedFunds(200);
            JSONObject jsonWallet = wallet.toJson();
            JSONObject jsonList = el.toJson();

            JsonWriterListAndWallet writer = new JsonWriterListAndWallet("./data/testWriterEmptyListSomeBalanceSomeAccumulated.json");
            writer.open();
            writer.write(jsonList, jsonWallet);
            writer.close();

            JsonReaderExpensesList listReader = new JsonReaderExpensesList("./data/testWriterEmptyListSomeBalanceSomeAccumulated.json");
            el = listReader.read();
            assertEquals("Joe", el.getOwnerName());
            assertEquals(0, el.getList().size());

            JsonReaderWallet walletReader = new JsonReaderWallet("./data/testWriterEmptyListSomeBalanceSomeAccumulated.json");
            wallet = walletReader.read();
            checkWallet("Joe", 200, 50, wallet);

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralListNegativeBalance() {
        try {
            ExpensesList el = new ExpensesList("Jane");
            Wallet wallet = new Wallet("Jane");
            wallet.addTotalAccumulatedFunds(95);
            wallet.addFunds(60.5);
            Expense sunglasses = new Expense("sunglasses", 15.50);
            Expense headphones = new Expense("headphones", 85.0);
            el.addExpense(sunglasses, wallet);
            el.addExpense(headphones, wallet);

            JSONObject jsonWallet = wallet.toJson();
            JSONObject jsonList = el.toJson();

            JsonWriterListAndWallet writer = new JsonWriterListAndWallet("./data/testWriterGeneralListNegativeBalance.json");
            writer.open();
            writer.write(jsonList, jsonWallet);
            writer.close();

            JsonReaderExpensesList listReader = new JsonReaderExpensesList("./data/testWriterGeneralListNegativeBalance.json");
            el = listReader.read();
            checkList("Jane", 2, el);
            checkExpense("sunglasses", 15.50, sunglasses);
            checkExpense("headphones", 85.0, headphones);

            JsonReaderWallet walletReader = new JsonReaderWallet("./data/testWriterGeneralListNegativeBalance.json");
            wallet = walletReader.read();
            checkWallet("Jane", 95, -40, wallet);

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralListNoBalance() {
        try {
            ExpensesList el = new ExpensesList("Jane");
            Wallet wallet = new Wallet("Jane");
            wallet.addTotalAccumulatedFunds(100.50);
            wallet.addFunds(100.50);
            Expense sunglasses = new Expense("sunglasses", 15.50);
            Expense headphones = new Expense("headphones", 85.0);
            el.addExpense(sunglasses, wallet);
            el.addExpense(headphones, wallet);

            JSONObject jsonWallet = wallet.toJson();
            JSONObject jsonList = el.toJson();

            JsonWriterListAndWallet writer = new JsonWriterListAndWallet("./data/testWriterGeneralListNoBalance.json");
            writer.open();
            writer.write(jsonList, jsonWallet);
            writer.close();

            JsonReaderExpensesList listReader = new JsonReaderExpensesList("./data/testWriterGeneralListNoBalance.json");
            el = listReader.read();
            checkList("Jane", 2, el);
            checkExpense("sunglasses", 15.50, sunglasses);
            checkExpense("headphones", 85.0, headphones);

            JsonReaderWallet walletReader = new JsonReaderWallet("./data/testWriterGeneralListNoBalance.json");
            wallet = walletReader.read();
            checkWallet("Jane", 100.5, 0, wallet);

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralListPositiveBalance() {
        try {
            ExpensesList el = new ExpensesList("Jane");
            Wallet wallet = new Wallet("Jane");
            wallet.addTotalAccumulatedFunds(550.55);
            wallet.addFunds(250);
            Expense sunglasses = new Expense("sunglasses", 15.50);
            Expense headphones = new Expense("headphones", 85.0);
            el.addExpense(sunglasses, wallet);
            el.addExpense(headphones, wallet);

            JSONObject jsonWallet = wallet.toJson();
            JSONObject jsonList = el.toJson();

            JsonWriterListAndWallet writer = new JsonWriterListAndWallet("./data/testWriterGeneralListPositiveBalance.json");
            writer.open();
            writer.write(jsonList, jsonWallet);
            writer.close();

            JsonReaderExpensesList listReader = new JsonReaderExpensesList("./data/testWriterGeneralListPositiveBalance.json");
            el = listReader.read();
            checkList("Jane", 2, el);
            checkExpense("sunglasses", 15.50, sunglasses);
            checkExpense("headphones", 85.0, headphones);

            JsonReaderWallet walletReader = new JsonReaderWallet("./data/testWriterGeneralListPositiveBalance.json");
            wallet = walletReader.read();
            checkWallet("Jane", 550.55, 149.5, wallet);

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }


}
