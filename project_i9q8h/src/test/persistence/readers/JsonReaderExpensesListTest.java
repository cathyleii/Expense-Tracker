package persistence.readers;

import model.Expense;
import model.ExpensesList;
import org.junit.jupiter.api.Test;
import persistence.JsonTest;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderExpensesListTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReaderExpensesList reader = new JsonReaderExpensesList("./data/noSuchFile.json");
        try {
            ExpensesList el = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyList() {
        JsonReaderExpensesList reader = new JsonReaderExpensesList("./data/testReaderEmptyListNoBalanceNothingAccumulated.json");
        try {
            ExpensesList el = reader.read();
            checkList("Joe", 0, el);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralList() {
        JsonReaderExpensesList reader = new JsonReaderExpensesList("./data/testReaderGeneralListPositiveBalance.json");
        try {
            ExpensesList el = reader.read();
            List<Expense> expenses = el.getList();
            checkList("Jane", 2, el);
            checkExpense("sunglasses", 15.5, expenses.get(0));
            checkExpense("headphones", 85, expenses.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
